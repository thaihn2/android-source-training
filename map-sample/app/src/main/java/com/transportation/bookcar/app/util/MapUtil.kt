package com.transportation.bookcar.app.util

import com.google.android.gms.maps.model.LatLng
import com.transportation.bookcar.domain.pojo.Route
import java.util.*

object MapUtil {

    fun parserDirectionRoutes(routes: List<Route>): List<LatLng> {
        val points = arrayListOf<LatLng>()
        if (routes.isNotEmpty()) {
            val route = routes[0]
            if (route.legs.isNotEmpty()) {
                route.legs.forEach { leg ->
                    if (leg.steps.isNotEmpty()) {
                        leg.steps.forEach { step ->
                            points.add(LatLng(step.startLocation.lat, step.startLocation.lng))
                            val list = decodePoly(step.polyline.points)
                            points.addAll(list)
                            points.add(LatLng(step.endLocation.lat, step.endLocation.lng))
                        }
                    }
                }
            }
        }
        return points
    }

    fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5,
                    lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }
}
