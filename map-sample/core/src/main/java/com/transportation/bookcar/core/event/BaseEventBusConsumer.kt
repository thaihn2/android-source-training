package com.transportation.bookcar.core.event

import io.reactivex.functions.Consumer

/**
 * Provide wrapper for process event data with Rx
 */

abstract class BaseEventBusConsumer : Consumer<RxEvent<*>> {
    override fun accept(pRxEvent: RxEvent<*>) {
        onReceiveEvent(pRxEvent)
    }
    
    /**
     * handle event logic
     * @param pRxEvent
     */
    protected abstract fun onReceiveEvent(pRxEvent: RxEvent<*>)
}
