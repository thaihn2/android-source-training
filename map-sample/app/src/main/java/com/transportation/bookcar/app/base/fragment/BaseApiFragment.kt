package com.transportation.bookcar.app.base.fragment

import android.view.View
import android.widget.Toast
import butterknife.BindView
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.base.api.ApiViewContract
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.view.CoreFragment

/**
 * Created on 3/15/2018.
 */
abstract class BaseApiFragment<P : AndroidPresenter<*>> : CoreFragment<P>(), ApiViewContract {

//    override fun showLoading() {
//
//    }
//
//    override fun hideLoading() {
//
//    }
    
    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}
