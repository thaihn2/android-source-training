package com.transportation.bookcar.app.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.transportation.bookcar.app.BuildConfig
import com.transportation.bookcar.app.R
import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.core.presenter.CorePresenter
import com.transportation.bookcar.domain.interactor.MapInteractor
import com.transportation.bookcar.domain.interactor.SearchLocationInteractor
import com.transportation.bookcar.domain.pojo.Route
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import javax.inject.Inject


/**
 * Created by Dao Thanh HA on 11/20/2018.
 */

class HomePresenter @Inject constructor(private val mContext: Context, view: HomeViewContract) :
        CorePresenter<HomeViewContract>(view), HomePresenterContract,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private val TAG = HomePresenter::class.java.simpleName

        const val UPDATE_INTERVAL = 60 * 1000L
        const val FASTEST_INTERVAL = 1000L
    }

    @Inject
    lateinit var mapInteractor: MapInteractor

    private var mGoogleApiClient: GoogleApiClient? = null

    private var mLocationRequest: LocationRequest = LocationRequest.create()

    private var mCurrentLocation: Location? = null

    @Inject
    lateinit var searchLocationInteractor: SearchLocationInteractor

    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            locationResult ?: return
            getCurrentLocationAndStopLocationUpdate(locationResult.lastLocation)
        }
    }

    private fun getCurrentLocationAndStopLocationUpdate(locationResult: Location?) {
        if (locationResult == null) view.getCurrentLocationFail(mContext.getString(R.string.home_current_location_invalid))
        else {
            mCurrentLocation = locationResult
            view.getCurrentLocationSuccess(mCurrentLocation)
        }
        stopLocationUpdate()
    }

    override fun onConnected(bundle: Bundle?) {
        startLocationUpdatesWithPermissionCheck()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun onPermissionGrand() {
        loadCurrentLocation()
    }

    override fun onSearchLocation(namePlace: String) {

        if (namePlace.isNotEmpty()) {
            view.showSearching(true)
            searchLocation(namePlace)
        } else view.showSearching(false)
    }

    override fun showSearchResult(isShowing: Boolean) {
        view.showSearchResult(isShowing)
    }

    private fun searchLocation(namePlace: String) {
        searchLocationInteractor.getLocations(BuildConfig.SERVER_API_KEY, namePlace).dispatchAndSubscribe {
            doOnSuccess {
                view.showSearching(false)
                if (!it.candidates.isNullOrEmpty()) {
                    view.searchLocationSuccess(it)
                    view.showNoResultData(false)
                } else {
                    view.showNoResultData(true)
                }
            }

            doShowError {
                view.searchLocationFail(it)
                view.showSearching(false)
            }
        }
    }

    override fun onConnectionSuspended(onConnectionSuspended: Int) {
        mGoogleApiClient?.connect()
    }

    override fun onViewCreate() {
        super<CorePresenter>.onViewCreate()
        checkPermissionLocation()
    }

    override fun getAddressFromLatLng(latLng: LatLng) {
        try {
            val geoCoder: Geocoder? = Geocoder(mContext, Locale.getDefault())
            val addresses: List<Address>? = geoCoder?.getFromLocation(latLng.latitude, latLng.longitude, 1) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.isNullOrEmpty())
                view.getLocationAddressByLatLong("", latLng)
            else
                view.getLocationAddressByLatLong(addresses[0].getAddressLine(0), latLng)
        } catch (ex: Exception) {
            Log.d(TAG, "GeoCoder error: $ex")
        }
    }


    override fun getDirection(originLocation: LatLng?, destination: LatLng?) {
        if (originLocation == null || destination == null) {
            view.getDirectionFail()
            return
        } else {
            val origin = "${originLocation.latitude},${originLocation.longitude}"
            val target = "${destination.latitude},${destination.longitude}"
//            mapInteractor.getDirection(origin, target, BuildConfig.SERVER_API_KEY).dispatchAndSubscribe {
//                doOnSuccess {
//                    Log.d(TAG, "Page list Route: $it")
//                    view.getDirectionSuccess(getPathOnMap(it.results))
//                }
//
//                doOnError {
//                    view.hideLoading()
//                    it.message?.let { it1 -> view.showError(it1) }
//                }
//
//                doHideLoading {
//                    view.hideLoading()
//                }
//            }

            mapInteractor.getDirectionBetweenPlace(origin, target, BuildConfig.SERVER_API_KEY).dispatchAndSubscribe {
                doOnSuccess {
                    Log.d(TAG, "Page list Route: $it")
                    view.getDirectionSuccess(getPathOnMap(it.routes))
                }

                doOnError {
                    view.hideLoading()
                    it.message?.let { it1 -> view.showError(it1) }
                }

                doHideLoading {
                    view.hideLoading()
                }
            }
        }
    }

    private fun getPathOnMap(routes: List<Route>): List<LatLng> {
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

    private fun decodePoly(encoded: String): List<LatLng> {
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

    private fun checkPermissionLocation() {
        if (hasPermissionLocation()) {
            loadCurrentLocation()
        } else {
            view.requestPermissionLocation()
        }
    }

    private fun loadCurrentLocation() {
        if (mCurrentLocation != null) {
            view.getCurrentLocationSuccess(mCurrentLocation)
        } else {
            if (isPlayServices() && hasPermissionLocation() && isGpsEnable()) {
                mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                mLocationRequest.interval = UPDATE_INTERVAL
                mLocationRequest.fastestInterval = FASTEST_INTERVAL
                mGoogleApiClient = GoogleApiClient.Builder(mContext)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build()
                mGoogleApiClient?.connect()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdatesWithPermissionCheck() {
        if (hasPermissionLocation()) {
            LocationServices.getFusedLocationProviderClient(mContext).requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        }
    }

    private fun stopLocationUpdate() {
        if (hasPermissionLocation())
            LocationServices.getFusedLocationProviderClient(mContext).removeLocationUpdates(mLocationCallback)
    }

    private fun isPlayServices(): Boolean {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS
    }

    private fun hasPermissionLocation(): Boolean {
        return EasyPermissions.hasPermissions(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun isGpsEnable(): Boolean {
        val locationManager: LocationManager? = mContext.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    override fun onViewDestroy() {
        super<HomePresenterContract>.onViewDestroy()
        mGoogleApiClient?.disconnect()
    }
}
