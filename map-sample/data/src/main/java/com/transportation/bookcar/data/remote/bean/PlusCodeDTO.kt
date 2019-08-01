package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class PlusCodeDTO(
        @SerializedName("compound_code")
        val compound_code: String,
        @SerializedName("global_code")
        val global_code: String
)