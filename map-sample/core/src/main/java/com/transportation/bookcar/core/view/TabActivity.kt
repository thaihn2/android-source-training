package com.transportation.bookcar.core.view

import com.transportation.bookcar.core.adapter.BaseFragmentPagerAdapter
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter

/**
 * Created on 2/13/2018.
 */
abstract class TabActivity<P : AndroidPresenter<*>> : CoreActivity<P>() {
    
    protected lateinit var kTabAdapter: BaseFragmentPagerAdapter<*>
    
    override fun onBackPressed() {
        val activeFragment = kTabAdapter.getActiveFragment()
        if (!activeFragment.onBackPressed()) {
            finish()
            return
        }
    }
}
