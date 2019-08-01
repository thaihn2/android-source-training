package com.transportation.bookcar.app.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.transportation.bookcar.app.util.ValidateUtil
import com.transportation.bookcar.core.presenter.CorePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        view: LoginViewContract
) : CorePresenter<LoginViewContract>(view), LoginPresenterContract {

    override fun login(email: String, password: String) {
        if (!validLogin(email, password)) {
            return
        }
        view.showLoading()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    handleResponse(it)
                }
                .addOnFailureListener {
                    view.showFailMessage(it.message)
                }
    }

    private fun handleResponse(authResult: Task<AuthResult>) {
        view.hideLoading()
        if (authResult.isSuccessful) {
            view.showSuccessMessage()
        } else {
            view.showFailMessage(authResult.exception?.message)
        }
    }

    private fun validLogin(email: String, password: String): Boolean {
        if (!ValidateUtil.validateEmail(email)) {
            view.showValidEmailOrPassword()
            return false
        }

        if (!ValidateUtil.validatePassword(password)) {
            view.showValidEmailOrPassword()
            return false
        }
        return true
    }
}
