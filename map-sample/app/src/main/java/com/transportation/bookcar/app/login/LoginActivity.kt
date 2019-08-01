package com.transportation.bookcar.app.login

import android.os.Bundle
import android.view.View
import android.widget.*
import butterknife.BindView
import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.util.KeyboardUtil
import com.transportation.bookcar.core.view.CoreActivity

class LoginActivity : CoreActivity<LoginPresenterContract>(), LoginViewContract {

    val activityNavigator by lazy { navigator as AppActivityNavigator }

    @BindView(R.id.tv_register_login)
    lateinit var vCreateAccount: TextView

    @BindView(R.id.et_login_email)
    lateinit var vEmail: EditText

    @BindView(R.id.et_login_password)
    lateinit var vPassword: EditText

    @BindView(R.id.bt_login_submit)
    lateinit var vSubmit: Button

    @BindView(R.id.progress)
    lateinit var vProgress: ProgressBar

    override val layoutResId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vCreateAccount.setOnClickListener {
            activityNavigator.gotoRegisterScreen()
        }

        vSubmit.setOnClickListener {
            KeyboardUtil.hideKeyboard(this, vEmail)
            val email = vEmail.text.toString().trim()
            val password = vPassword.text.toString().trim()
            presenter.login(email, password)
        }
    }

    override fun showValidEmailOrPassword() {
        Toast.makeText(this, getString(R.string.error_login_invalid_email_or_password), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessMessage() {
        Toast.makeText(this, getString(R.string.title_login_success), Toast.LENGTH_SHORT).show()
        activityNavigator.gotoHomeScreen()
    }

    override fun showFailMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        vProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vProgress.visibility = View.GONE
    }
}
