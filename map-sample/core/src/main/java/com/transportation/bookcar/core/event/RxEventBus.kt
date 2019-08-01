package com.transportation.bookcar.core.event

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

/**
 * For send broadcast event to receiver. Its behavior like event bus or local broadcast manager
 */

object RxEventBus {
    
    private val bus = PublishSubject.create<RxEvent<*>>()!!
    
    fun subscribe(pConsumer: Consumer<RxEvent<*>>): Disposable {
        return bus.subscribe(pConsumer)
    }
    
    fun send(o: RxEvent<*>) {
        bus.onNext(o)
    }
    
    fun toObservable(): Observable<RxEvent<*>> = bus
    
    fun hasObservers(): Boolean = bus.hasObservers()
}
