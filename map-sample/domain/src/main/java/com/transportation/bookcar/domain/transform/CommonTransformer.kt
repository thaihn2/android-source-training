package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.DirectionDTO
import com.transportation.bookcar.data.remote.bean.PageListDTO
import com.transportation.bookcar.domain.pojo.Direction
import com.transportation.bookcar.domain.pojo.PageList

/**
 * Created on 5/16/2019.
 */
fun <T, R> PageListDTO<T>.toPageList(transFun: (T) -> R) = PageList<R>(
        page,
        results.map { transFun(it) },
        totalPages,
        totalResults
)

fun <T, R> DirectionDTO<T>.toDirection(transFun: (T) -> R) = Direction<R>(
        geocoderWaypoints.map {
            it.toGeocodedWaypoints()
        },
        routes.map { transFun(it) },
        status
)
