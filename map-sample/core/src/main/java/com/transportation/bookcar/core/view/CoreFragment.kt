package com.transportation.bookcar.core.view

import android.os.Bundle
import android.support.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import com.transportation.bookcar.core.FragmentNavigator
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView
import javax.inject.Inject

/**
 * Created on 2/1/2018.
 */

abstract class CoreFragment<P : AndroidPresenter<*>> : DaggerFragment(), AndroidView {
    @Inject
    lateinit var presenter: P
    @Inject
    lateinit var navigator: FragmentNavigator
    open val fragmentContainerId: Int = 0
    
    protected val compositeDisposable = CompositeDisposable()
    
    private lateinit var viewUnbinder: Unbinder
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onViewCreate()
    }
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = createView(savedInstanceState, inflater, container!!)
        viewUnbinder = ButterKnife.bind(this, view)
        initView()
        setupView()
        presenter.onViewCreatedView()
        return view
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
    
    override fun onDestroyView() {
        compositeDisposable.clear()
        presenter.onViewDestroy()
        viewUnbinder.unbind()
        super.onDestroyView()
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
    
    open fun onBackPressed(): Boolean = navigator.onBackPressed()
    
    /**
     * Make new fragment instance with additional argument
     *
     * @param argument for new fragment instance
     * @return new fragment instance
     */
    companion object {
        @JvmStatic
        fun newInstance(
                fragmentClass: Class<out CoreFragment<*>>,
                argument: Bundle? = null,
                sharedElementEnterTransition: Transition? = null,
                enterTransition: Transition? = null,
                exitTransition: Transition? = null
        ): CoreFragment<*> {
            val newFragment: CoreFragment<*> = fragmentClass.newInstance()
            try {
                newFragment.arguments = argument
                newFragment.sharedElementEnterTransition = sharedElementEnterTransition
                newFragment.enterTransition = enterTransition
                newFragment.exitTransition = exitTransition
            }
            catch (pE: Exception) {
                //                AppLogger.e(pE)
            }
            return newFragment
        }
        
    }
}
