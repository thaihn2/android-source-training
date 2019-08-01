package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class OpeningHoursDTO(
        @SerializedName("open_now")
        val open_now: Boolean
)