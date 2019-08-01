package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class PageListDTO<T>(
        @SerializedName("page")
        val page: Int = 0, // 1
        @SerializedName("routes")
        val results: List<T> = listOf(),
        @SerializedName("total_pages")
        val totalPages: Int = 0, // 993
        @SerializedName("total_results")
        val totalResults: Int = 0 // 19844
)
