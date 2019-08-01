package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class LocationDTO(
        @SerializedName("error_message")
        val error_message: String,
        @SerializedName("html_attributions")
        val html_attributions: List<Any>,
        @SerializedName("next_page_token")
        val next_page_token: String,
        @SerializedName("results")
        val results: List<ResultDTO>,
        @SerializedName("status")
        val status: String
)