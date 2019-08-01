package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class ResultDTO(
        @SerializedName("formatted_address")
        val formatted_address: String,
        @SerializedName("geometry")
        val geometry: GeometryDTO,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("opening_hours")
        val opening_hours: OpeningHoursDTO,
        @SerializedName("photos")
        val photos: List<PhotoDTO>,
        @SerializedName("place_id")
        val place_id: String,
        @SerializedName("plus_code")
        val plus_code: PlusCodeDTO,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("reference")
        val reference: String,
        @SerializedName("types")
        val types: List<String>,
        @SerializedName("user_ratings_total")
        val user_ratings_total: Int
)