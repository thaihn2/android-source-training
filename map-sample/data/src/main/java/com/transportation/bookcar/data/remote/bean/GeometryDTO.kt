package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class GeometryDTO(
        @SerializedName("location")
        val location: LocationGeometryDTO,
        @SerializedName("viewport")
        val viewport: ViewportDTO
)