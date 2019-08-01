package com.transportation.bookcar.core.event

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.os.ResultReceiver
import java.lang.ref.WeakReference

/**
 * Result receiver for handle callback
 */

abstract class BaseResultReceiver<T> : ResultReceiver {
    val reference: WeakReference<T>

    constructor(t: T) : super(Handler(Looper.getMainLooper())) {
        reference = WeakReference(t)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        val reference = reference.get() ?: return
        if (reference is Activity) {
            if (reference.isFinishing || reference.isDestroyed)
                return
        }
        if (reference is Fragment) {
            if (!reference.isAdded)
                return
        }
        onReceiveResult(resultCode, resultData, reference)
    }

    protected abstract fun onReceiveResult(pResultCode: Int, pResultData: Bundle?, pReference: T)
}
