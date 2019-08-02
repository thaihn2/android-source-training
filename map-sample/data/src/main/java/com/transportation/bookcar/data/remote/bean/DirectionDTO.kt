package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class DirectionDTO<T>(
        @SerializedName("geocoded_waypoints") val geocoderWaypoints: List<GeocodedWaypointsDTO>,

        @SerializedName("routes") val routes: List<T> = listOf(),

        @SerializedName("status") val status: String
)

data class GeocodedWaypointsDTO(
        @SerializedName("geocoder_status") val geocoderStatus: String,

        @SerializedName("place_id") val placeId: String,

        @SerializedName("types") val types: List<String>
)
