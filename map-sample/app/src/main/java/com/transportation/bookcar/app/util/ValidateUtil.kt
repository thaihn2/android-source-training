package com.transportation.bookcar.app.util

import android.util.Patterns
import java.util.regex.Pattern

object ValidateUtil {

    private const val MAX_LENGTH_TEXT = 100
    private const val MIN_LENGTH_PASSWORD = 8
    private const val oneMoreEmailPattern = "(?:[a-z0-9!#\$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

    fun validateEmail(email: String): Boolean {
        val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
        val otherMatcher = Pattern.compile(oneMoreEmailPattern).matcher(email)
        return matcher.matches() || otherMatcher.matches()
    }

    fun validateUserName(userName: String): Boolean {
        return userName.isNotBlank() && userName.length <= MAX_LENGTH_TEXT
    }

    fun validatePassword(password: String): Boolean {
        return password.length in MIN_LENGTH_PASSWORD..MAX_LENGTH_TEXT
    }
}
