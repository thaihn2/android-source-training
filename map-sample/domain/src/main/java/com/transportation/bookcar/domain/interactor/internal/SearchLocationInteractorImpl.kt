package com.transportation.bookcar.domain.interactor.internal

import android.util.Log
import com.transportation.bookcar.common.api.ApiException
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.domain.R
import com.transportation.bookcar.domain.interactor.SearchLocationInteractor
import com.transportation.bookcar.domain.pojo.Place
import com.transportation.bookcar.domain.transform.toLocation
import io.reactivex.Single
import javax.inject.Inject

class SearchLocationInteractorImpl @Inject constructor(
        private val networkUtil: NetworkUtil,
        private val resourceUtil: ResourceUtil,
        private val apiRepo: ApiClient
)
    : SearchLocationInteractor{
    override fun getLocations(apiKey: String, namePlace: String): Single<Place> {
        return if(networkUtil.isConnected()){
            apiRepo.getLocations(apiKey, namePlace).map {
                it.toLocation()
            }
        } else {
            Single.fromCallable{throw  ApiException(resourceUtil.getStringResource(R.string.network_error))}
        }
    }
}