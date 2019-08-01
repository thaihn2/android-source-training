package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class MovieDTO(
        @SerializedName("adult")
        val adult: Boolean = false, // false
        @SerializedName("backdrop_path")
        val backdropPath: String = "", // /tmM78qRhpg0i2Cky8Q8hXKASOXY.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        @SerializedName("id")
        val id: Int = 0, // 526050
        @SerializedName("original_language")
        val originalLanguage: String = "", // en
        @SerializedName("original_title")
        val originalTitle: String = "", // Little
        @SerializedName("overview")
        val overview: String = "", // A woman receives the chance to relive the life of her younger self, at a point in her life when the pressures of adulthood become too much for her to bear.
        @SerializedName("popularity")
        val popularity: Double = 0.0, // 75.302
        @SerializedName("poster_path")
        val posterPath: String = "", // /4MDB6jJl3U7xK1Gw64zIqt9pQA4.jpg
        @SerializedName("release_date")
        val releaseDate: String = "", // 2019-04-04
        @SerializedName("title")
        val title: String = "", // Little
        @SerializedName("video")
        val video: Boolean = false, // false
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0, // 6.1
        @SerializedName("vote_count")
        val voteCount: Int = 0 // 54
)
