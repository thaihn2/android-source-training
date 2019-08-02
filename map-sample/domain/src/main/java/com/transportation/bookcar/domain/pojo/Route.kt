package com.transportation.bookcar.domain.pojo

data class Route(
        val legs: List<Leg>,

        val overviewPolyline: OverviewPolyline,

        val summary: String
)

data class Leg(
        val distance: Distance,

        val duration: Duration,

        val endAddress: String,

        val endLocation: LatLng,

        val startAddress: String,

        val startLocation: LatLng,

        val steps: List<Step>
)

data class Step(
        val distance: Distance,

        val duration: Duration,

        val startLocation: LatLng,

        val endLocation: LatLng,

        val htmlInstructions: String,

        val polyline: OverviewPolyline,

        val travelMode: String,

        val maneuver: String?
)

data class LatLng(
        val lat: Double,

        val lng: Double
)

data class OverviewPolyline(
        val points: String
)

data class Distance(
        val text: String,

        val value: Int
)

data class Duration(
        val text: String,

        val value: Int
)
