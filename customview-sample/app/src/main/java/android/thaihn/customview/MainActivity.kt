package android.thaihn.customview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.customview.seekbar.SeekBarActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSeekBar.setOnClickListener {
            startActivity(Intent(this, SeekBarActivity::class.java))
        }

        buttonSnow.setOnClickListener {

        }
    }
}
