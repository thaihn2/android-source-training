package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.LocationDTO
import com.transportation.bookcar.data.remote.bean.ResultDTO
import com.transportation.bookcar.domain.pojo.Candidate
import com.transportation.bookcar.domain.pojo.Place

fun LocationDTO.toLocation() = Place(results.toCandidate())

fun List<ResultDTO>.toCandidate(): List<Candidate> {
//    val candidates: MutableList<Candidate> = ArrayList()

   return map{
       Candidate(it.formatted_address, it.name, it.geometry.location.lat, it.geometry.location.lng)
    }

//    for (result in this) {
//        candidates.add(Candidate(result.formatted_address, result.name, result.geometry.location.lat, result.geometry.location.lng))
//    }
//
//    return candidates
}
