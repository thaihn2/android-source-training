package android.thaihn.customview.seekbar.widget

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.thaihn.customview.R
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class SeekBarCustom : View {

    interface OnSeekBarChangeListener {
        fun onProgressChange(progress: Int, fromUser: Boolean)

        fun onStopTrackingTouch()

        fun onStartTrackingTouch()
    }

    companion object {
        private val TAG = SeekBarCustom::class.java.simpleName

        private const val DEFAULT_RADIUS_WHEEL = 20F
        private const val DEFAULT_WIDTH_RECTANGLE = 20F
        private const val DEFAULT_MAX_PROGRESS = 100
        private const val DEFAULT_WIDTH = 80
        private const val DEFAULT_HEIGHT = 300
        private const val DEFAULT_RATIO = 1.25F
    }

    private var mOnSeekBarChangeListener: OnSeekBarChangeListener? = null

    private var mWidthRectangle: Float = DEFAULT_WIDTH_RECTANGLE

    private var mRadiusWheel: Float = DEFAULT_RADIUS_WHEEL

    // Attributes
    private var mColorWheel: Int = 0
    private var mColorBackground: Int = 0
    private var mMax: Int = DEFAULT_MAX_PROGRESS
    private var mProgress: Int = 0

    private var mPositionX: Float = 0F
    private var mPositionY: Float = 0F
    private var mMaxHeightBottom: Float = 0F
    private var mMaxHeightTop: Float = 0F

    private lateinit var mPaintWheel: Paint
    private lateinit var mPaintRectangle: Paint

    private var isDrawing = true

    private lateinit var mGestureDetector: GestureDetector

    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        getAttributes(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        getAttributes(context, attrs)
    }

    fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener) {
        mOnSeekBarChangeListener = listener
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            Log.d(TAG, "onDraw()")
            drawRectangle(it, isDrawing)
            drawCircle(mPositionX, mPositionY, canvas, true)
            drawCircleStroke(mPositionX, mPositionY, canvas)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth: Int = getLayoutSize(widthMeasureSpec, DEFAULT_WIDTH)
        val mHeight: Int = getLayoutSize(heightMeasureSpec, DEFAULT_HEIGHT)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
        calculatorDefault(mWidth, mHeight)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_MOVE,
                MotionEvent.ACTION_SCROLL -> {
                    Log.d(TAG, "Start track")
                    trackTouchEvent(it)
                    invalidate()
                }
                MotionEvent.ACTION_CANCEL -> {
                    Log.d(TAG, "ACTION CANCEL")
                    invalidate()
                }
                else -> {
                    Log.d(TAG, "Another action: ${it.action}")
                }
            }
        }
        return mGestureDetector.onTouchEvent(event)
    }

    private fun trackTouchEvent(event: MotionEvent) {
        val posY = event.y
        mMaxHeightBottom = height - mRadiusWheel * DEFAULT_RATIO
        mMaxHeightTop = mRadiusWheel * DEFAULT_RATIO
        when {
            posY in mMaxHeightTop..mMaxHeightBottom -> {
                if (isDrawing) {
                    isDrawing = false
                }
                mPositionX = (width / 2).toFloat()
                mPositionY = event.y
                val progress = mMax - (mMax * ((posY - mRadiusWheel) / (height - mRadiusWheel * 2)))
                mOnSeekBarChangeListener?.onProgressChange(progress.toInt(), true)
            }
            posY > mMaxHeightBottom -> {
                mOnSeekBarChangeListener?.onProgressChange(0, true)
                mPositionX = (width / 2).toFloat()
                mPositionY = height - mRadiusWheel * DEFAULT_RATIO
            }
            posY < mMaxHeightTop -> {
                mOnSeekBarChangeListener?.onProgressChange(DEFAULT_MAX_PROGRESS, true)
                mPositionX = (width / 2).toFloat()
                mPositionY = mRadiusWheel * DEFAULT_RATIO
            }
        }
    }

    private fun getLayoutSize(measureSpec: Int, default: Int): Int = when (MeasureSpec.getMode(measureSpec)) {
        MeasureSpec.EXACTLY -> {
            //Must be this size
            MeasureSpec.getSize(measureSpec)
        }
        MeasureSpec.AT_MOST -> {
            //Can't be bigger than...
            Math.min(default, MeasureSpec.getSize(measureSpec))
//          return DEFAULT_WIDTH
        }
        MeasureSpec.UNSPECIFIED -> {
            // Be whatever you want
            default
        }
        else -> {
            0
        }
    }

    private fun drawRectangle(canvas: Canvas, isDrawBackground: Boolean) {
        Log.d(TAG, "drawRectangle(): width:$width --- height:$height --- isDrawBackground:$isDrawBackground")
        val center = Point(width / 2, height / 2)
        val left: Float = center.x - (mWidthRectangle / 2)
        val top = mRadiusWheel * DEFAULT_RATIO
        val right = center.x + (mWidthRectangle / 2)
        val bottom = height - mRadiusWheel * DEFAULT_RATIO
//        mRect = new Rect(left, top, right, bottom) // no radius
        val rectF = RectF(left, top, right, bottom)

        if (isDrawBackground) {
            mPaintRectangle.color = mColorBackground
        } else {
            // Default color
            val colors = getListColorsCircle()
            val shader = LinearGradient(0F, height.toFloat(), 0F, mPositionY, colors, null, Shader.TileMode.CLAMP)
            mPaintRectangle.shader = shader
        }

        canvas.drawRoundRect(rectF, 10F, 10F, mPaintRectangle)
    }

    private fun drawCircle(x: Float, y: Float, canvas: Canvas, isBackground: Boolean) {
        Log.d(TAG, "drawCircle(): x:$x---y:$y---radiusWheel:$mRadiusWheel")

        if (isBackground) {
            mPaintWheel.color = mColorWheel
        } else {
            // Whatever color you want
        }
        canvas.drawCircle(x, y, mRadiusWheel, mPaintWheel)
    }

    private fun drawCircleStroke(x: Float, y: Float, canvas: Canvas) {
        val paintShader = Paint().apply {
            this.shader = SweepGradient(x, y, getListColors(), null)
            style = Paint.Style.STROKE
            strokeWidth = mRadiusWheel / 4
        }
        canvas.drawCircle(x, y, mRadiusWheel, paintShader)
//        mContext?.let {
//            val paint = Paint().apply {
//                color = ContextCompat.getColor(it, R.color.colorBlack)
//                style = Paint.Style.STROKE
//                strokeWidth = mRadiusWheel / 4
//            }
//            canvas.drawCircle(x, y, mRadiusWheel, paint)
//        }
    }

    private fun getAttributes(context: Context, attrs: AttributeSet?) {
        Log.d(TAG, "getAttributes(): width:$width---height:$height")
        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar, 0, 0)
        typeArray?.let {
            mMax = it.getInt(R.styleable.CustomSeekBar_max, DEFAULT_MAX_PROGRESS)
            mProgress = it.getInt(R.styleable.CustomSeekBar_progress, 0)
            mColorWheel = it.getColor(R.styleable.CustomSeekBar_colorWheel, ContextCompat.getColor(context, R.color.colorAccent))
            mColorBackground = it.getColor(R.styleable.CustomSeekBar_colorBackground, ContextCompat.getColor(context, R.color.colorPrimaryDark))
        }

        mPaintWheel = Paint()
        mPaintRectangle = Paint()

        // Create attributes default
        mContext = context

        // Init listener scroll
        val listenerScroll = MyGestureListener()
        mGestureDetector = GestureDetector(context, listenerScroll)
    }

    private fun calculatorDefault(_width: Int, _height: Int) {
        mPositionX = _width / 2F
        mPositionY = _height - mRadiusWheel * DEFAULT_RATIO

        mRadiusWheel = _width / 4F
        mWidthRectangle = _width / 10F
    }

    private fun getListColors(): IntArray {
        return intArrayOf(resources.getColor(R.color.redColor), resources.getColor(R.color.blueColor), resources.getColor(R.color.greenColor), resources.getColor(R.color.tealColor), resources.getColor(R.color.orangeColor))
    }

    private fun getListColorsCircle(): IntArray {
        return intArrayOf(resources.getColor(R.color.md_red_700), resources.getColor(R.color.md_pink_700), resources.getColor(R.color.md_green_700), resources.getColor(R.color.md_teal_700), resources.getColor(R.color.md_orange_700), resources.getColor(R.color.md_light_green_700), resources.getColor(R.color.md_purple_700), resources.getColor(R.color.md_deep_purple_700), resources.getColor(R.color.md_indigo_700))
    }

    class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
    }
}
