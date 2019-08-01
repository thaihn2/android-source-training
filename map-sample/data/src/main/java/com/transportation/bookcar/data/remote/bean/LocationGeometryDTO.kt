package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class LocationGeometryDTO(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double
)