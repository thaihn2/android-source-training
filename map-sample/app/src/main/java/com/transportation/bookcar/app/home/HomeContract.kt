package com.transportation.bookcar.app.home

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.transportation.bookcar.app.base.api.ApiViewContract
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.domain.pojo.Place
import com.transportation.bookcar.domain.pojo.Route

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */
interface HomePresenterContract : AndroidPresenter<HomeViewContract> {
    fun onPermissionGrand()
    fun onSearchLocation(namePlace: String)
    fun getAddressFromLatLng(latLng: LatLng)
    fun showSearchResult(isShowing: Boolean)
    fun getDirection(originLocation: LatLng?, destination: LatLng?)
}

interface HomeViewContract : ApiViewContract {
    fun requestPermissionLocation()
    fun getLocationAddressByLatLong(address: String, latLng: LatLng)
    fun getCurrentLocationSuccess(location: Location?)
    fun getCurrentLocationFail(err: String)

    // Search location with name
    fun searchLocationSuccess(place: Place)

    fun searchLocationFail(error: String)

    fun showSearching(isSearching: Boolean)
    fun showNoResultData(isNoResult: Boolean)
    fun showSearchResult(isShowing: Boolean)
    fun getDirectionRoutes(routes: List<Route>)
    fun getDirectionPoints(points: List<LatLng>)
    fun getDirectionFail()
}
