package com.transportation.bookcar.app.home

import android.Manifest
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import butterknife.BindView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.transportation.bookcar.app.R
import com.transportation.bookcar.core.view.CoreActivity
import com.transportation.bookcar.domain.pojo.Candidate
import com.transportation.bookcar.domain.pojo.Place
import kotlinx.android.synthetic.main.layout_loading.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : CoreActivity<HomePresenterContract>(),
        HomeViewContract,
        OnMapReadyCallback,
        EasyPermissions.PermissionCallbacks,
        SearchResultHolder.ChooseLocationListener {

    companion object {
        private val TAG = HomeActivity::class.java.simpleName

        const val RC_LOCATION_PERM = 100
        const val ZOOM_DEFAULT = 15F
        private const val POLYLINE_WIDTH = 8F
    }

    private var mMap: GoogleMap? = null

    private var mCurrentLatLng: LatLng? = null
    private var mDestinationLatLng: LatLng? = null
    private var mCurrentMarker: Marker? = null
    private var mDestinationMarker: Marker? = null
    private var mPolyline: Polyline? = null

    @BindView(R.id.ed_home_current_location)
    lateinit var edHomeCurrentLocation: EditText

    @BindView(R.id.ed_home_place)
    lateinit var edHomePlace: EditText

    @BindView(R.id.home_search_result)
    lateinit var homeSearchResult: RelativeLayout

    @BindView(R.id.rv_home_search_result)
    lateinit var rvHomeSearchResult: RecyclerView

    @BindView(R.id.pb_home_search_loading)
    lateinit var pbHomeSearchLoading: ProgressBar

    @BindView(R.id.tv_home_no_result)
    lateinit var tvHomeNoResult: TextView

    @BindView(R.id.btn_home_book_car)
    lateinit var btnHomeBookCar: Button

    private var candidaces: List<Candidate> = ArrayList()

    private lateinit var searchResultAdapter: HomeSearchResultAdapter

    override fun onMapReady(map: GoogleMap?) {
        this.mMap = map
        mMap?.setOnMapClickListener { latLng ->
            presenter.showSearchResult(false)
            mDestinationMarker?.remove()
            mDestinationLatLng = latLng
            mDestinationLatLng?.let {
                mDestinationMarker = addMarker(it)
                presenter.getAddressFromLatLng(it)
            }

            mPolyline?.remove()
            presenter.getDirection(mCurrentLatLng, mDestinationLatLng)
        }
    }

    override fun showLoading() {
        flLoading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        flLoading?.visibility = View.GONE
    }

    override fun showError(error: String) {
        Toast.makeText(this@HomeActivity, error, Toast.LENGTH_SHORT).show()
    }

    override val layoutResId: Int
        get() = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMapView()
        initAdapter()
        initListener()
    }

    private fun initListener() {
        edHomePlace.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.onSearchLocation(edHomePlace.text.trim().toString())
            }
            return@setOnEditorActionListener false
        }

        btnHomeBookCar.setOnClickListener {

        }
    }

    override fun showSearching(isSearching: Boolean) {
        if (isSearching) {
            pbHomeSearchLoading.visibility = View.VISIBLE
            homeSearchResult.visibility = View.VISIBLE
        } else {
            pbHomeSearchLoading.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        searchResultAdapter = HomeSearchResultAdapter(candidaces, this)
        rvHomeSearchResult.layoutManager = LinearLayoutManager(this)
        rvHomeSearchResult.adapter = searchResultAdapter
    }

    override fun getLocationAddressByLatLong(address: String, latLng: LatLng) {
        Log.d(TAG, "getLocationAddressByLatLong(): address:$address")
        when (latLng) {
            mCurrentLatLng -> {
                edHomeCurrentLocation.setText(address)
                mCurrentMarker?.apply {
                    title = address
                    showInfoWindow()
                }
            }
            mDestinationLatLng -> {
                edHomePlace.setText(address)
                mDestinationMarker?.apply {
                    title = address
                    showInfoWindow()
                }
            }
        }
    }

    override fun getDirectionSuccess(points: List<LatLng>) {
        Log.d(TAG, "getDirectionSuccess(): points:$points")
        if (points.isNotEmpty()) {
            val routes = arrayListOf<LatLng>()
            routes.addAll(points)
            mDestinationLatLng?.let { routes.add(it) }
            val rectLine = PolylineOptions().width(POLYLINE_WIDTH).color(Color.RED)
            routes.forEach {
                rectLine.add(it)
            }

            // Add route on Map
            mPolyline = mMap?.addPolyline(rectLine)
        }
    }

    override fun getDirectionFail() {
        Toast.makeText(this, getString(R.string.error_home_can_not_get_direction), Toast.LENGTH_SHORT).show()
    }

    private fun initMapView() {
        val mapFragment: SupportMapFragment? = supportFragmentManager?.findFragmentById(R.id.fr_home_map) as? SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun addMarker(latLng: LatLng): Marker? {
        val marker = mMap?.addMarker(MarkerOptions().position(latLng))
        marker?.showInfoWindow()
        return marker
    }

    override fun searchLocationSuccess(place: Place) {
        presenter.showSearchResult(true)
        candidaces = place.candidates
        searchResultAdapter.resetCandidateList(place.candidates)
    }

    override fun showSearchResult(isShowing: Boolean) {
        if (isShowing)
            homeSearchResult.visibility = View.VISIBLE
        else
            homeSearchResult.visibility = View.GONE
    }

    override fun showNoResultData(isNoResult: Boolean) {
        if (isNoResult) {
            tvHomeNoResult.visibility = View.VISIBLE
        } else {
            tvHomeNoResult.visibility = View.GONE
        }
    }

    override fun onChooseLocation(position: Int) {
        val candidate = candidaces[position]
        homeSearchResult.visibility = View.GONE

        mDestinationMarker?.remove()
        mDestinationLatLng = LatLng(candidate.lat, candidate.lng)
        mDestinationLatLng?.let {
            mDestinationMarker = addMarker(it)
            mDestinationMarker?.apply {
                title = candidate.formatted_address
                showInfoWindow()
            }
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_DEFAULT))
        }
    }

    override fun searchLocationFail(error: String) {
        pbHomeSearchLoading.visibility = View.GONE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun requestPermissionLocation() {
        EasyPermissions.requestPermissions(
                this,
                getString(R.string.home_rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        presenter.onPermissionGrand()
    }

    override fun getCurrentLocationSuccess(location: Location?) {
        Log.d(TAG, "getCurrentLocationSuccess(): $location")
        mCurrentLatLng = if (location == null) null else LatLng(location.latitude, location.longitude)
        mCurrentLatLng?.let {
            mCurrentMarker = addMarker(it)
            presenter.getAddressFromLatLng(it)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, ZOOM_DEFAULT))
        }
    }

    override fun getCurrentLocationFail(err: String) {
        Toast.makeText(this@HomeActivity, err, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}
