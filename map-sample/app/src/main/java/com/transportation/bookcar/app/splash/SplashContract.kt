package com.transportation.bookcar.app.splash

import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView

/**
 * Created on 2/5/2018.
 */
interface SplashViewContract : AndroidView {
    fun gotoHomeScreen()

    fun gotoLoginScreen()
}

interface SplashPresenterContract : AndroidPresenter<SplashViewContract> {
    fun showSplash()

    fun checkLogin()
}
