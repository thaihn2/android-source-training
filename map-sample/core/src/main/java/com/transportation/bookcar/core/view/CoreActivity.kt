package com.transportation.bookcar.core.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.adapter.BaseFragmentPagerAdapter
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */
abstract class CoreActivity<P : AndroidPresenter<*>> : DaggerAppCompatActivity(),
                                                       AndroidView {
    open val fragmentContainerId: Int = android.R.id.content
    
    @Inject
    lateinit var presenter: P
    @Inject
    lateinit var navigator: ActivityNavigator
    
    private lateinit var viewUnbinder: Unbinder
    protected val compositeDisposable = CompositeDisposable()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: View = createView(
                savedInstanceState,
                layoutInflater,
                findViewById<ViewGroup>(android.R.id.content)
        )
        setContentView(view)
        viewUnbinder = ButterKnife.bind(this)
        presenter.onViewCreate()
        initView()
        setupView()
    }
    
    override fun onStart() {
        super.onStart()
        presenter.onViewStart()
    }
    
    override fun onResume() {
        super.onResume()
        presenter.onViewResume()
    }
    
    override fun onPause() {
        presenter.onViewPause()
        super.onPause()
    }
    
    override fun onStop() {
        presenter.onViewStop()
        super.onStop()
    }
    
    override fun onDestroy() {
        compositeDisposable.clear()
        presenter.onViewDestroy()
        viewUnbinder.unbind()
        super.onDestroy()
    }
    
    override fun onBackPressed() {
        if (!navigator.onBackPressed()) {
            finish()
        }
    }
    
    abstract class CoreTabActivity<P : AndroidPresenter<*>> : CoreActivity<P>() {
        open lateinit var pagerAdapter: BaseFragmentPagerAdapter<*>
        
        override fun onBackPressed() {
            val activeFragment = pagerAdapter.getActiveFragment()
            if (!activeFragment.onBackPressed()) {
                finish()
                return
            }
        }
    }
}
