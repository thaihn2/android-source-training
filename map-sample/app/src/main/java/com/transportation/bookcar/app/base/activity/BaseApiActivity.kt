package com.transportation.bookcar.app.base.activity

import com.transportation.bookcar.app.base.api.ApiViewContract
import com.transportation.bookcar.core.base.Mvp
import com.transportation.bookcar.core.view.CoreActivity

/**
 * Created on 3/15/2018.
 */
abstract class BaseApiActivity<P : Mvp.AndroidPresenter<*>> : CoreActivity<P>(), ApiViewContract {
//    @BindView(R.id.fl_loading)
//    @Nullable
//    lateinit var vLoading: View
//
//    override fun showSplash() {
//        if (this::vLoading.isInitialized) {
//            vLoading.visibility = View.VISIBLE
//        }
//    }
//
//    override fun hideLoading() {
//        if (this::vLoading.isInitialized) {
//            vLoading.visibility = View.GONE
//        }
//    }
    
}
