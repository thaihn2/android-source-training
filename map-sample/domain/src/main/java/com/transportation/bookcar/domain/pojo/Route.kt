package com.transportation.bookcar.domain.pojo

data class Route(
        val legs: List<Leg>,

        val overview_polyline: OverviewPolyline
)

data class Leg(
        val steps: List<Step>,

        val end_address: String,

        val start_address: String
)

data class Step(
        val start_location: LatLng,

        val end_location: LatLng,

        val html_instructions: String,

        val polyline: OverviewPolyline
)

data class LatLng(
        val lat: Double,

        val lng: Double
)

data class OverviewPolyline(
        val points: String
)
