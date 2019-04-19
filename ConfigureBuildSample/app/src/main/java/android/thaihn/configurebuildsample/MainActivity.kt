package android.thaihn.configurebuildsample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.configurebuildsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainBinding.tvName.text = getString(R.string.app_name)

        mainBinding.btnTest.setOnClickListener {
            mainBinding.tvName.text = "Test"
        }
    }
}
