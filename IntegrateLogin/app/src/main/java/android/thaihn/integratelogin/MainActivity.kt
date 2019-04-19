package android.thaihn.integratelogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.integratelogin.facebook.FacebookLoginActivity
import android.thaihn.integratelogin.facebook.util.FacebookUtil
import android.thaihn.integratelogin.google.GoogleLoginActivity
import android.thaihn.integratelogin.twitter.TwitterLoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_facebook.setOnClickListener {
            startActivity(Intent(applicationContext, FacebookLoginActivity::class.java))
        }

        btn_google.setOnClickListener {
            startActivity(Intent(applicationContext, GoogleLoginActivity::class.java))
        }

        btn_twitter.setOnClickListener {
            startActivity(Intent(applicationContext, TwitterLoginActivity::class.java))
        }

        FacebookUtil.getKeyStoreDebug(this)
    }
}
