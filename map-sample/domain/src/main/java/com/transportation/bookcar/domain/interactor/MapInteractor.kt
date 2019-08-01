package com.transportation.bookcar.domain.interactor

import com.transportation.bookcar.domain.pojo.PageList
import com.transportation.bookcar.domain.pojo.Route
import io.reactivex.Single

interface MapInteractor {
    fun getDirection(origin: String, destination: String, key: String): Single<PageList<Route>>
}
