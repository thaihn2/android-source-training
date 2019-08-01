package com.transportation.bookcar.domain.pojo

data class Candidate(
        val formatted_address: String,
        val name: String,
        val lat: Double,
        val lng: Double
)