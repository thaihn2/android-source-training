package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName

data class RouteDTO(
        @SerializedName("legs") val legs: List<LegDTO>,

        @SerializedName("overview_polyline") val overviewPolyline: OverviewPolylineDTO,

        @SerializedName("summary") val summary: String
)

data class LegDTO(
        @SerializedName("distance") val distance: DistanceDTO,

        @SerializedName("duration") val duration: DurationDTO,

        @SerializedName("end_address") val endAddress: String,

        @SerializedName("end_location") val enLocation: LatLngDTO,

        @SerializedName("start_address") val startAddress: String,

        @SerializedName("start_location") val startLocation: LatLngDTO,

        @SerializedName("steps") val steps: List<StepDTO>
)

data class StepDTO(
        @SerializedName("distance") val distance: DistanceDTO,

        @SerializedName("duration") val duration: DurationDTO,

        @SerializedName("start_location") val startLocation: LatLngDTO,

        @SerializedName("end_location") val endLocation: LatLngDTO,

        @SerializedName("html_instructions") val htmlInstructions: String,

        @SerializedName("polyline") val polyline: OverviewPolylineDTO,

        @SerializedName("travel_mode") val travelMode: String,

        @SerializedName("maneuver") val maneuver: String? // turn-left
)

data class LatLngDTO(
        @SerializedName("lat") val lat: Double,

        @SerializedName("lng") val lng: Double
)

data class OverviewPolylineDTO(
        @SerializedName("points") val points: String
)

data class DistanceDTO(
        @SerializedName("text") val text: String,
        @SerializedName("value") val value: Int
)

data class DurationDTO(
        @SerializedName("text") val text: String,
        @SerializedName("value") val value: Int
)

