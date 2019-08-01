package com.transportation.bookcar.domain.interactor

import com.transportation.bookcar.domain.pojo.Place
import io.reactivex.Single

interface SearchLocationInteractor {
    fun getLocations(apiKey: String, namePlace: String): Single<Place>
}