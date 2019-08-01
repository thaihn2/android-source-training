package com.transportation.bookcar.domain.pojo


data class PageList<T>(
        val page: Int = 0, // 1
        val results: List<T> = listOf(),
        val totalPages: Int = 0, // 993
        val totalResults: Int = 0 // 19844
)
