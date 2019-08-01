package com.transportation.bookcar.common.api

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import io.reactivex.exceptions.CompositeException
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import com.transportation.bookcar.core.AppLogger

/**
 * Created on 3/20/2018.
 */

class ApiCallListener<T> : DisposableSingleObserver<T>() {
    private var onSuccessHandler: ((T) -> Unit)? = null
    private var onErrorHandler: ((Throwable) -> Unit)? = null
    private val onForceLogoutHandler: ((Throwable) -> Unit)? = ApiConfig.onForceLogoutHandler
    private val onForceUpdateHandler: ((Throwable) -> Unit)? = ApiConfig.onForceUpdateHandler
    private var onShowErrorHandler: ((String) -> Unit)? = null
    private var onHideLoadingHandler: (() -> Unit)? = null
    
    fun doOnSuccess(handle: ((T) -> Unit)?) {
        onSuccessHandler = handle
    }
    
    fun doHideLoading(handle: (() -> Unit)?) {
        onHideLoadingHandler = handle
    }
    
    fun doShowError(handle: ((String) -> Unit)?) {
        onShowErrorHandler = handle
    }
    
    fun doOnError(handle: ((Throwable) -> Unit)?) {
        onErrorHandler = handle
    }
    
    override fun onError(error: Throwable) {
        invokeHideLoading()
        AppLogger.e(error)
        when (error) {
            is HttpException      -> {
                handleHttpCallError(error)
            }
            is CompositeException -> {
                invokeShowError(ApiConfig.defaultErrorMessage)
            }
            is ApiException       -> {
                //handle fore update and force logout
                when (error.code) {
                    ApiConfig.FORCE_LOGOUT_CODE -> onForceLogoutHandler?.invoke(error)
                    ApiConfig.FORCE_UPDATE_CODE -> onForceUpdateHandler?.invoke(error)
                
                //handle additional error on end point
                    else                        -> {
                        invokeOnError(error)
                        val msg = if (!ApiConfig.IS_DEBUG && error.code == ERROR_CODE_GENERAL) ApiConfig.defaultErrorMessage else error.message
                        if (!msg.isNullOrEmpty()) {
                            invokeShowError(error.message ?: "")
                        }
                    }
                }
            }
            else                  -> {
                //handle other exception
                val msg = if (ApiConfig.IS_DEBUG) error.message else ApiConfig.defaultErrorMessage
                if (!msg.isNullOrEmpty()) {
                    invokeShowError(msg!!)
                }
            }
        }
    }
    
    private fun handleHttpCallError(error: HttpException) {
        var body: ResponseBody? = null
        try {
            body = error.response()!!.errorBody()
        }
        catch (ex: NullPointerException) {
            AppLogger.log(0, ex)
        }
        val errorBodyObj: SimpleApiRespond? = getParsedError(String(body!!.bytes()))
        AppLogger.e(error)
        if (errorBodyObj != null) {
            onShowErrorHandler?.let { handler ->
                if (!errorBodyObj.message.isBlank()) {
                    handler.invoke(errorBodyObj.message)
                }
            }
        }
        else {
            if (ApiConfig.IS_DEBUG) {
                invokeShowError(error.toString())
            }
            else {
                invokeShowError(ApiConfig.defaultErrorMessage)
            }
        }
    }
    
    private fun getParsedError(errorJson: String): SimpleApiRespond? {
        var errorBodyObj: SimpleApiRespond? = null
        try {
            errorBodyObj = Gson().fromJson<SimpleApiRespond>(
                    errorJson,
                    SimpleApiRespond::class.java
            )
        }
        catch (ex: NullPointerException) {
            AppLogger.log(0, ex)
        }
        catch (ex: JsonParseException) {
            AppLogger.log(0, ex)
        }
        catch (ex: JsonSyntaxException) {
            AppLogger.log(0, ex)
        }
        return errorBodyObj
    }
    
    override fun onSuccess(t: T) {
        try {
            onHideLoadingHandler?.invoke()
            onSuccessHandler?.invoke(t)
        }
        catch (ex: Exception) {
            try {
                onShowErrorHandler?.invoke(ApiConfig.defaultErrorMessage)
            }
            catch (ex: Exception) {
                AppLogger.e(ex)
            }
        }
    }
    
    private fun invokeOnError(error: Throwable) {
        try {
            onErrorHandler?.invoke(error)
        }
        catch (ex: Exception) {
            AppLogger.e(ex)
        }
    }
    
    private fun invokeShowError(message: String) {
        try {
            onShowErrorHandler?.invoke(message)
        }
        catch (ex: Exception) {
            AppLogger.e(ex)
        }
    }
    
    private fun invokeHideLoading() {
        try {
            onHideLoadingHandler?.invoke()
        }
        catch (ex: Exception) {
            try {
                onShowErrorHandler?.invoke(ApiConfig.defaultErrorMessage)
            }
            catch (ex: Exception) {
                AppLogger.e(ex)
            }
        }
    }
    
}
