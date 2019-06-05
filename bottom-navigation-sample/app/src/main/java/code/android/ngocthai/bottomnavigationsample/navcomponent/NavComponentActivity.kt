package code.android.ngocthai.bottomnavigationsample.navcomponent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import code.android.ngocthai.bottomnavigationsample.R
import kotlinx.android.synthetic.main.activity_nav_component.*

class NavComponentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_component)

        Navigation.findNavController(this, R.id.nav_host_component)?.let { navigation ->
            NavigationUI.setupWithNavController(bottomNavigation, navigation)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_component).navigateUp()
    }
}

