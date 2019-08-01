package com.transportation.bookcar.common.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.transportation.bookcar.common.api.ApiCallListener

/**
 * Created on 3/16/2018.
 */
fun <T> Single<T>.dispatchToIoThread(): Single<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.dispatchAndSubscribe(init: ApiCallListener<T>.() -> Unit): DisposableSingleObserver<T> {
    return dispatchToIoThread()
            .subscribeListener(init)
}

fun <T> Single<T>.subscribeListener(init: ApiCallListener<T>.() -> Unit): DisposableSingleObserver<T> {
    val listener = ApiCallListener<T>()
    listener.init()
    return this.subscribeWith(listener)
}

