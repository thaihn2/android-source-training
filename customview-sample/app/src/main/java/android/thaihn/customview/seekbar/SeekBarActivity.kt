package android.thaihn.customview.seekbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.customview.R
import android.thaihn.customview.seekbar.widget.SeekBarCustom
import android.util.Log
import kotlinx.android.synthetic.main.activity_seek_bar.*

class SeekBarActivity : AppCompatActivity() {

    companion object {
        private val TAG = SeekBarActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seek_bar)

        seekBarCustom.setOnSeekBarChangeListener(object : SeekBarCustom.OnSeekBarChangeListener {

            override fun onProgressChange(progress: Int, fromUser: Boolean) {
                Log.d(TAG, "Progress: $progress")
                textResult.text = "Progress: $progress %"
            }

            override fun onStartTrackingTouch() {
            }

            override fun onStopTrackingTouch() {

            }
        })
    }
}
