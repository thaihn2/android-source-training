package com.transportation.bookcar.app.splash

import com.google.firebase.auth.FirebaseAuth
import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.core.AppLogger
import com.transportation.bookcar.core.presenter.CorePresenter
import io.reactivex.Single
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */

class SplashPresenter @Inject constructor(view: SplashViewContract) :
        CorePresenter<SplashViewContract>(view),
        SplashPresenterContract {
    /**
     * Show splash in short time
     */
    override fun showSplash() {
        Single.timer(SPLASH_TIME, MILLISECONDS).dispatchAndSubscribe {
            doOnSuccess { checkLogin() }
        }
    }

    override fun checkLogin() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            view.gotoHomeScreen()
        } else {
            view.gotoLoginScreen()
        }
    }

    companion object {
        const val SPLASH_TIME = 2000L
    }
}
