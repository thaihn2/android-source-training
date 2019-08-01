package com.transportation.bookcar.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created on 2/1/2018.
 */
interface Mvp {
    interface View
    interface Presenter
    interface AndroidView : View {
        val layoutResId: Int
        /**
         * Created inflate view layout and onViewCreate reference to android view, attach android event handler
         * @param viewState save view state
         * @param inflater inflater to inflate view resource
         * @param container parent view
         */
        fun createView(
                viewState: Bundle?,
                layoutInflater: LayoutInflater,
                container: ViewGroup
        ): android.view.View {
            return layoutInflater.inflate(layoutResId, container, false)
        }
        
        /**
         * Setup android view reference
         */
        fun initView() {}
        
        /**
         * Setup view adapter, view listener
         */
        fun setupView() {}
    }
    
    interface AndroidPresenter<V : AndroidView> : Presenter {
        fun onViewCreate() {}
        fun onViewCreatedView() {}
        fun onViewStart() {}
        fun onViewPause() {}
        fun onViewResume() {}
        fun onViewStop() {}
        fun onViewDestroy() {}
        fun onBackPressed(): Boolean = false
    }
}
