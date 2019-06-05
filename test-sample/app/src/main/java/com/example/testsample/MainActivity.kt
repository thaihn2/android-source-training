package com.example.testsample

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.testsample.enity.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var mPreferencesHelper: SharedPreferencesHelper? = null

    private var mEmailValidator: EmailValidator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEmailValidator = EmailValidator()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mPreferencesHelper = SharedPreferencesHelper(sharedPreferences)

        showUserInfo()

        buttonSave.setOnClickListener {
            saveUserInfo()
        }
    }

    private fun showUserInfo() {
        val userInfo = mPreferencesHelper?.getPersonalInfo()

        if (userInfo != null) {
            textResult.text = userInfo.toString()
        } else {
            textResult.text = "Empty"
        }
    }

    private fun saveUserInfo() {
        val name = editTextName.text.toString().trim()
        val dateOfBirth = Calendar.getInstance()
        dateOfBirth.set(editTextDateOfBirth.year, editTextDateOfBirth.month, editTextDateOfBirth.dayOfMonth)
        val email = editTextEmail.text.toString().trim()

        val userInfo = UserInfo(name, dateOfBirth.timeInMillis, email)

        val result = mPreferencesHelper?.savePersonalInfo(userInfo)
        result?.let {
            if (it) {
                Toast.makeText(applicationContext, "Save success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Save fail", Toast.LENGTH_SHORT).show()
            }
        }

        showUserInfo()
    }
}
