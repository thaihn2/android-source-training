package com.transportation.bookcar.app

import android.content.Intent
import com.transportation.bookcar.app.home.HomeActivity
import com.transportation.bookcar.app.login.LoginActivity
import com.transportation.bookcar.app.register.RegisterActivity
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.FragmentNavigator
import com.transportation.bookcar.core.view.CoreActivity
import com.transportation.bookcar.core.view.CoreFragment
import javax.inject.Inject

/**
 * Created on 2/2/2018.
 */
class AppActivityNavigator @Inject constructor(activity: CoreActivity<*>) :
        ActivityNavigator(activity) {

    fun gotoHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    fun gotoLoginScreen() {
        activity.apply {
            startActivity(Intent(activity, LoginActivity::class.java))
            finish()
        }
    }

    fun gotoRegisterScreen() {
        activity.startActivity(Intent(activity, RegisterActivity::class.java))
    }
}

class AppFragmentNavigator @Inject constructor(fragment: CoreFragment<*>) : FragmentNavigator(
        fragment
) {

}
