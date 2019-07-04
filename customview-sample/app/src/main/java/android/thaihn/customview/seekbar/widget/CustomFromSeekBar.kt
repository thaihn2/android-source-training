package android.thaihn.customview.seekbar.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.SeekBar

class CustomFromSeekBar : SeekBar {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onDraw(canvas: Canvas?) {
        canvas?.rotate(-90F)
        canvas?.translate(-height.toFloat(), 0F)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) {
            return false
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                val progress = max - (max * event.y / height).toInt()
                if (progress in 0..100) {
                    setProgress(progress)
                    onSizeChanged(width, height, 0, 0)
                }
            }

            MotionEvent.ACTION_CANCEL -> {
            }
        }

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }
}