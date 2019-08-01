package com.transportation.bookcar.app.splash

import android.os.Bundle
import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.app.R
import com.transportation.bookcar.core.view.CoreActivity

class SplashActivity : CoreActivity<SplashPresenterContract>(), SplashViewContract {

    val activityNavigator by lazy { navigator as AppActivityNavigator }

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.showSplash()
    }

    override fun gotoHomeScreen() {
        activityNavigator.gotoHomeScreen()
    }

    override fun gotoLoginScreen() {
        activityNavigator.gotoLoginScreen()
    }
}
