package com.transportation.bookcar.domain.pojo

data class Direction<T>(
        val geocoderWaypoints: List<GeocodedWaypoints>,

        val routes: List<T> = listOf(),

        val status: String
)

data class GeocodedWaypoints(
        val geocoderStatus: String,

        val placeId: String,

        val types: List<String>
)
