package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class RouteDTO(
        @SerializedName("legs") val legs: List<LegDTO>,

        @SerializedName("overview_polyline") val overview_polyline: OverviewPolylineDTO
)

data class LegDTO(
        @SerializedName("steps") val steps: List<StepDTO>,

        @SerializedName("end_address") val end_address: String,

        @SerializedName("start_address") val start_address: String
)

data class StepDTO(
        @SerializedName("start_location") val start_location: LatLngDTO,

        @SerializedName("end_location") val end_location: LatLngDTO,

        @SerializedName("html_instructions") val html_instructions: String,

        @SerializedName("polyline") val polyline: OverviewPolylineDTO
)

data class LatLngDTO(
        @SerializedName("lat") val lat: Double,

        @SerializedName("lng") val lng: Double
)

data class OverviewPolylineDTO(
        @SerializedName("points") val points: String
)
