package com.transportation.bookcar.app.register

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.util.KeyboardUtil
import com.transportation.bookcar.app.util.UserType
import com.transportation.bookcar.core.view.CoreActivity

class RegisterActivity : CoreActivity<RegisterPresenterContract>(), RegisterViewContract {

    val ativityNavigator by lazy { navigator as AppActivityNavigator }

    @BindView(R.id.et_register_name)
    lateinit var vName: EditText

    @BindView(R.id.et_register_email)
    lateinit var vEmail: EditText

    @BindView(R.id.et_register_password)
    lateinit var vPassword: EditText

    @BindView(R.id.bt_register_submit)
    lateinit var vSubmit: Button

    @BindView(R.id.tv_register_login)
    lateinit var vLogin: TextView

    @BindView(R.id.spinner_register_user_type)
    lateinit var vSpinnerUser: Spinner

    @BindView(R.id.progress)
    lateinit var vProgress: ProgressBar

    private val mUserTypeList = arrayListOf(UserType.User.type, UserType.Driver.type)

    override val layoutResId: Int
        get() = R.layout.activity_register

    private var mUserSelection = UserType.User.type

    private val itemSelectedSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mUserSelection = parent?.getItemAtPosition(position).toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vSubmit.setOnClickListener {
            KeyboardUtil.hideKeyboard(this, vEmail)
            val email = vEmail.text.toString().trim()
            val name = vName.text.toString().trim()
            val password = vPassword.text.toString().trim()
            presenter.register(name, email, password, mUserSelection)
        }

        vLogin.setOnClickListener {
            onBackPressed()
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mUserTypeList)
        vSpinnerUser.adapter = spinnerAdapter
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vSpinnerUser.onItemSelectedListener = itemSelectedSpinner
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        vProgress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vProgress.visibility = View.GONE
    }

    override fun showSuccessMessage() {
        Toast.makeText(this, getString(R.string.msg_login_success), Toast.LENGTH_SHORT).show()
        ativityNavigator.gotoHomeScreen()
    }

    override fun showFailMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showException(authResult: Task<AuthResult>) {
        if (authResult.exception is FirebaseAuthUserCollisionException) {
            Toast.makeText(this, getString(R.string.error_register_email_already_exit), Toast.LENGTH_SHORT).show()
        } else if (authResult.exception is FirebaseAuthWeakPasswordException) {
            Toast.makeText(this, getString(R.string.error_register_password_not_strong), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showValidEmailOrPassword() {
        Toast.makeText(this, getString(R.string.error_login_invalid_email_or_password), Toast.LENGTH_SHORT).show()
    }

    override fun showValidName() {
        Toast.makeText(this, getString(R.string.error_register_name_invalid), Toast.LENGTH_SHORT).show()
    }
}
