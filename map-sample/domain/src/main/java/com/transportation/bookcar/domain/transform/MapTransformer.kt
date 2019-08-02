package com.transportation.bookcar.domain.transform

import com.transportation.bookcar.data.remote.bean.*
import com.transportation.bookcar.domain.pojo.*

fun GeocodedWaypointsDTO.toGeocodedWaypoints() = GeocodedWaypoints(geocoderStatus, placeId, types)

fun RouteDTO.toRoute() = Route(legs.map { it.toLeg() }, overviewPolyline.toOverviewPolyline(), summary)

fun LegDTO.toLeg() = Leg(
        distance.toDistance(), duration.toDuration(), endAddress, enLocation.toLatLng(),
        endAddress, enLocation.toLatLng(), steps.map { it.toStep() }
)

fun StepDTO.toStep() = Step(
        distance.toDistance(), duration.toDuration(), startLocation.toLatLng(),
        endLocation.toLatLng(), htmlInstructions, polyline.toOverviewPolyline(), travelMode, maneuver
)

fun LatLngDTO.toLatLng() = LatLng(lat, lng)

fun OverviewPolylineDTO.toOverviewPolyline() = OverviewPolyline(points)

fun DurationDTO.toDuration() = Duration(text, value)

fun DistanceDTO.toDistance() = Distance(text, value)
