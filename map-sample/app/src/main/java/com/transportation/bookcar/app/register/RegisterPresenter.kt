package com.transportation.bookcar.app.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.transportation.bookcar.app.util.FireBaseConfig
import com.transportation.bookcar.app.util.ValidateUtil
import com.transportation.bookcar.core.presenter.CorePresenter
import com.transportation.bookcar.domain.pojo.User
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
        view: RegisterViewContract
) : CorePresenter<RegisterViewContract>(view), RegisterPresenterContract {

    override fun register(name: String, email: String, password: String, userType: String) {
        if (!validRegister(name, email, password)) {
            return
        }
        view.showLoading()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    handleResponse(it, name, userType)
                }
                .addOnFailureListener {
                    view.showFailMessage(it.message)
                }
    }

    private fun handleResponse(authResult: Task<AuthResult>, name: String, userType: String) {
        if (authResult.isSuccessful) {
            saveUserToDatabase(name, userType)
        } else {
            view.hideLoading()
            view.showException(authResult)
        }
    }

    private fun saveUserToDatabase(name: String, userType: String) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseUser?.let {
            val user = User(it.email, it.uid, name, it.uid, userType)
            FirebaseDatabase.getInstance().reference
                    .child(FireBaseConfig.REFERENCE_USERS)
                    .child(it.uid)
                    .setValue(user)
        }
        view.hideLoading()
        view.showSuccessMessage()
    }

    private fun validRegister(name: String, email: String, password: String): Boolean {
        if (!ValidateUtil.validateUserName(name)) {
            view.showValidName()
            return false
        }

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
