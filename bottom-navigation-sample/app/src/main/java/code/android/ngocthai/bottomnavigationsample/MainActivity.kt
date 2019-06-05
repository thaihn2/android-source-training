package code.android.ngocthai.bottomnavigationsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import code.android.ngocthai.bottomnavigationsample.basic.BottomNavigationActivity
import code.android.ngocthai.bottomnavigationsample.custom.CustomActivity
import code.android.ngocthai.bottomnavigationsample.navcomponent.NavComponentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBasic.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }

        buttonNavComponent.setOnClickListener {
            startActivity(Intent(this, NavComponentActivity::class.java))
        }

        buttonCustom.setOnClickListener {
            startActivity(Intent(this, CustomActivity::class.java))
        }
    }
}
