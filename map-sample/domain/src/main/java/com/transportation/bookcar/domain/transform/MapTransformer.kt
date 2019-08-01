package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.*
import com.transportation.bookcar.domain.pojo.*

fun RouteDTO.toRoute() = Route(legs.map { it.toLeg() }, overview_polyline.toOverviewPolyline())

fun LegDTO.toLeg() = Leg(steps.map { it.toStep() }, end_address, start_address)

fun StepDTO.toStep() = Step(start_location.toLatLng(), end_location.toLatLng(), html_instructions, polyline.toOverviewPolyline())

fun LatLngDTO.toLatLng() = LatLng(lat, lng)

fun OverviewPolylineDTO.toOverviewPolyline() = OverviewPolyline(points)
