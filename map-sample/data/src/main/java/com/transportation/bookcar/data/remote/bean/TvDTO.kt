package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class TvDTO(
        @SerializedName("backdrop_path")
        val backdropPath: String = "", // /qsD5OHqW7DSnaQ2afwz8Ptht1Xb.jpg
        @SerializedName("first_air_date")
        val firstAirDate: String = "", // 2011-04-17
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        @SerializedName("id")
        val id: Int = 0, // 1399
        @SerializedName("name")
        val name: String = "", // Game of Thrones
        @SerializedName("origin_country")
        val originCountry: List<String> = listOf(),
        @SerializedName("original_language")
        val originalLanguage: String = "", // en
        @SerializedName("original_name")
        val originalName: String = "", // Game of Thrones
        @SerializedName("overview")
        val overview: String = "", // Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.
        @SerializedName("popularity")
        val popularity: Double = 0.0, // 650.105
        @SerializedName("poster_path")
        val posterPath: String = "", // /u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0, // 8.2
        @SerializedName("vote_count")
        val voteCount: Int = 0 // 5843
)
