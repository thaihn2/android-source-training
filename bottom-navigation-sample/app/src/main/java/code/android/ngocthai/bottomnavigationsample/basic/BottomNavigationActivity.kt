package code.android.ngocthai.bottomnavigationsample.basic

import android.content.Context
import android.icu.text.AlphabeticIndex
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import code.android.ngocthai.bottomnavigationsample.R
import code.android.ngocthai.bottomnavigationsample.fragment.DashboardFragment
import code.android.ngocthai.bottomnavigationsample.fragment.HomeFragment
import code.android.ngocthai.bottomnavigationsample.fragment.NotificationFragment
import kotlinx.android.synthetic.main.activity_bottom_naivgation.*

class BottomNavigationActivity : AppCompatActivity() {

    companion object {
        private val TAG = BottomNavigationActivity::class.java.simpleName
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                putFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                putFragment(DashboardFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                putFragment(NotificationFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_naivgation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_home

        // Set dynamic menu
//        bottom_navigation.inflateMenu(R.menu.navigation_simple)

        // Change icon
//        bottom_navigation.menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_android_black_24dp)

        changeIcon()
    }

    private fun putFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit()
    }

    private fun changeIcon() {
    }
}
