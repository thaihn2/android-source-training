package android.thaihn.integratelogin.facebook

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.integratelogin.R
import android.thaihn.integratelogin.databinding.ActivityFacebookLoginBinding
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookLoginActivity : AppCompatActivity() {

    companion object {
        private val TAG = FacebookLoginActivity::class.java.simpleName

        private const val PERMISSION_EMAIL = "email"
        private const val PERMISSION_PUBLIC_PROFILE = "public_profile"
        private const val PERMISSION_USER_FRIENDS = "user_friends"
        private const val PERMISSION_BIRTH_DAY = "user_birthday"

    }

    private lateinit var facebookLoginBinding: ActivityFacebookLoginBinding

    private lateinit var mCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        facebookLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_facebook_login)

        // Init Facebook

        mCallbackManager = CallbackManager.Factory.create()

        facebookLoginBinding.loginButton.setReadPermissions(createPermission())
        // If you are using in a fragment, call loginButton.setFragment(this)

        facebookLoginBinding.loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {

            override fun onCancel() {
                Log.d(TAG, "onCancel")
                Toast.makeText(applicationContext, "onCancel()", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                error?.printStackTrace()
                Log.d(TAG, "Error code: ${error.hashCode()}")
                Toast.makeText(applicationContext, "Error Code:${error.hashCode()} --- Message:${error?.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "Login result: $result")
                result?.let { loginResult ->
                    updateUi(loginResult)
                }
            }
        })

        // Custom button
        LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.d(TAG, "onCancel")
                Toast.makeText(applicationContext, "onCancel()", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "Login result: $result")
                result?.let {
                    updateUi(it)
                }
            }

            override fun onError(error: FacebookException?) {
                error?.printStackTrace()
                Log.d(TAG, "Error code: ${error.hashCode()}")
                Toast.makeText(applicationContext, "Error Code:${error.hashCode()} --- Message:${error?.message}", Toast.LENGTH_SHORT).show()
            }
        })

        facebookLoginBinding.customButton.setOnClickListener {
            loginCustomButton()
        }

        facebookLoginBinding.logoutButton.setOnClickListener {
            logout()
        }

        updateUi(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUi(result: LoginResult?) {
        var accessToken = ""
        result?.let {
            accessToken = it.accessToken.toString()
        }
        val user = Profile.getCurrentProfile()

        facebookLoginBinding.tvStatus.text = "Status: ${checkLoginStatus()}"
        facebookLoginBinding.tvNotification.text = "accessToken:$accessToken \n\n profile:$user"
    }

    private fun checkLoginStatus(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    private fun loginCustomButton() {
        LoginManager.getInstance().logInWithReadPermissions(this, createPermission())
    }

    private fun createPermission(): List<String> {
        return arrayListOf(PERMISSION_EMAIL, PERMISSION_PUBLIC_PROFILE, PERMISSION_USER_FRIENDS, PERMISSION_BIRTH_DAY)
    }

    private fun logout() {
        LoginManager.getInstance().logOut()
        updateUi(null)
    }
}
