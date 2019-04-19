package android.thaihn.integratelogin.twitter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.integratelogin.R
import android.util.Log
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.main.activity_twitter_login.*


class TwitterLoginActivity : AppCompatActivity() {

    companion object {
        private val TAG = TwitterLoginActivity::class.java.simpleName
    }

    private lateinit var mTwitterAuthClient: TwitterAuthClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_login)

        mTwitterAuthClient = TwitterAuthClient()

        btn_login_twitter.setOnClickListener {
            customLogin()
        }

        btn_logout_twitter.setOnClickListener {
            logout()
        }

        login_button.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                Log.d(TAG, "success:result:$result")
                result?.let {
                    updateUI(it)
                }
            }

            override fun failure(exception: TwitterException?) {
                Log.d(TAG, "Failure: $exception")
                exception?.printStackTrace()
                Toast.makeText(applicationContext, exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        updateUI(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login_button.onActivityResult(requestCode, resultCode, data)
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data)
    }

    private fun customLogin() {
        mTwitterAuthClient.authorize(this, object : Callback<TwitterSession>() {
            override fun failure(exception: TwitterException?) {
                Log.d(TAG, "Failure: $exception")
                exception?.printStackTrace()
                Toast.makeText(applicationContext, exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun success(result: Result<TwitterSession>?) {
                Log.d(TAG, "success:result:$result")
                result?.let {
                    updateUI(it)
                }
            }
        })
    }

    private fun logout() {
        TwitterCore.getInstance().sessionManager.clearActiveSession()
        updateUI(null)
    }

    private fun updateUI(result: Result<TwitterSession>?) {
        result?.let {
            getUser(it.data)
            return
        }

        val session = TwitterCore.getInstance().sessionManager.activeSession
        val authorToken = session?.authToken
        val token = authorToken?.token
        val secret = authorToken?.secret

        tv_status.text = "Status Login: ${session?.authToken != null}"
        tv_token.text = "Token:$token \n TokenSecret:$secret"
        session?.let {
            getUser(it)
        }
    }

    private fun getUser(session: TwitterSession) {
        TwitterCore.getInstance().getApiClient(session).accountService
                .verifyCredentials(true, true, false)
                .enqueue(object : Callback<User>() {
                    override fun success(result: Result<User>) {
                        val name = result.data.name
                        val userName = result.data.screenName
                        val profileImageUrl = result.data.profileImageUrl.replace("_normal", "")

                        tv_notification.text = "name:$name \n userName:$userName \n profileImageUrl:$profileImageUrl"
                    }

                    override fun failure(exception: TwitterException) {
                        Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                        tv_notification.text = "Error, No data"
                    }
                })
    }
}
