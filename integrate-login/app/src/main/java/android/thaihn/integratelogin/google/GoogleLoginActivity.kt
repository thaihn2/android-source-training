package android.thaihn.integratelogin.google

import android.annotation.SuppressLint
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.thaihn.integratelogin.R
import android.thaihn.integratelogin.databinding.ActivityGoogleLoginBinding
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLoginActivity : AppCompatActivity() {

    companion object {
        private val TAG = GoogleLoginActivity::class.java.simpleName

        private const val REQUEST_CODE_SIGN_IN = 1
    }

    private lateinit var mGoogleLoginBinding: ActivityGoogleLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGoogleLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_google_login)

//        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()

        val webClientId = "779671502565-q27gv7hs2boc6l1vtfecicn7al8su8e5.apps.googleusercontent.com"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
//                .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
                .requestProfile()
                .requestIdToken(webClientId)
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUi(account)

        mGoogleLoginBinding.signInButton.setSize(SignInButton.SIZE_STANDARD)

        mGoogleLoginBinding.signInButton.setOnClickListener {
            signIn()
        }

        mGoogleLoginBinding.signOutButton.setOnClickListener {
            signOut()
        }

        mGoogleLoginBinding.disconnectButton.setOnClickListener {
            disconnectAccount()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completeTask: Task<GoogleSignInAccount>) {
        try {
            val account = completeTask.getResult(ApiException::class.java)
            account?.let {
                signInWithCredential(it)
            }
            updateUi(account)
        } catch (ex: ApiException) {
            ex.printStackTrace()
            updateUi(null)
            Log.d(TAG, "Sign-In failed with code: ${ex.statusCode}")
        }
    }

    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    private fun signInWithCredential(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser
                        Log.d(TAG, "Firebase: CurrentUser:$user")
                    } else {
                        Log.d(TAG, "signInWithCredential:failure", it.exception)
                        Toast.makeText(applicationContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    Log.d(TAG, "SignOut: ${it.result}")
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Sign out success", Toast.LENGTH_SHORT).show()
                        mGoogleLoginBinding.signInButton.visibility = View.VISIBLE
                    }
                }
    }

    private fun disconnectAccount() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this) {
                    Log.d(TAG, "DisconnectAccount: ${it.result}")
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Disconnect account success", Toast.LENGTH_SHORT).show()
                        mGoogleLoginBinding.signInButton.visibility = View.VISIBLE
                    }
                }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(account: GoogleSignInAccount?) {
        account?.let {
            mGoogleLoginBinding.signInButton.visibility = View.GONE

            val email = it.email
            val name = it.displayName
            val token = it.idToken
            val isExpired = it.isExpired
            val id = it.id
            val serverAuthCode = it.serverAuthCode
            mGoogleLoginBinding.tvAccount.text = " email:$email \n name:$name \n id: $id \n isExpired:$isExpired" +
                    "\n serverAuthCode:$serverAuthCode"
            mGoogleLoginBinding.tvToken.text = token
            return
        }
        mGoogleLoginBinding.signInButton.visibility = View.VISIBLE
        mGoogleLoginBinding.tvAccount.text = account.toString()
    }
}
