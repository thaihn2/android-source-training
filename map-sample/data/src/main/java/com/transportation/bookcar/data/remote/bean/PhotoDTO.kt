package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class PhotoDTO(
        @SerializedName("height")
        val height: Int,
        @SerializedName("html_attributions")
        val html_attributions: List<String>,
        @SerializedName("photo_reference")
        val photo_reference: String,
        @SerializedName("width")
        val width: Int
)