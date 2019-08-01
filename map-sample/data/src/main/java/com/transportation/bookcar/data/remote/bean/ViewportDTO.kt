package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class ViewportDTO(
        @SerializedName("northeast")
        val northeast: LocationGeometryDTO,
        @SerializedName("southwest")
        val southwest: LocationGeometryDTO
)