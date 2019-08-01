package com.transportation.bookcar.core.presenter

import io.reactivex.disposables.CompositeDisposable
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView

/**
 * Created on 2/1/2018.
 */
abstract class CorePresenter<V : AndroidView>(val view: V) : AndroidPresenter<V> {
    
    protected val compositeDisposable = CompositeDisposable()
    
    override fun onViewDestroy() {
        compositeDisposable.clear()
    }
}
