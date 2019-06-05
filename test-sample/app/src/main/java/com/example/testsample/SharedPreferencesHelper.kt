package com.example.testsample

import android.content.SharedPreferences
import com.example.testsample.enity.UserInfo
import java.util.*

class SharedPreferencesHelper(private val mPreference: SharedPreferences) {

    companion object {
        const val KEY_NAME = "key_name"
        const val KEY_DOB = "key_dob"
        const val KEY_EMAIL = "key_email"
    }

    fun savePersonalInfo(userInfo: UserInfo): Boolean {
        mPreference.edit()?.let { editor ->
            editor.putString(KEY_NAME, userInfo.name)
            editor.putLong(KEY_DOB, userInfo.dateOfBirth)
            editor.putString(KEY_EMAIL, userInfo.email)

            return editor.commit()
        }
        return false
    }

    fun getPersonalInfo(): UserInfo {
        val name = mPreference.getString(KEY_NAME, "")
        val dobMillisecond = mPreference.getLong(KEY_DOB, Calendar.getInstance().timeInMillis)
        val email = mPreference.getString(KEY_EMAIL, "")

        return UserInfo(name, dobMillisecond, email)
    }
}
