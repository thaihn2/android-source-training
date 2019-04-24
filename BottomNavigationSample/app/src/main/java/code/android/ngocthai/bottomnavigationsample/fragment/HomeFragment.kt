package code.android.ngocthai.bottomnavigationsample.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import code.android.ngocthai.bottomnavigationsample.R

class HomeFragment : Fragment() {

    companion object {

        private val TAG = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
