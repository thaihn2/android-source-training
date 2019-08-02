package com.transportation.bookcar.data.remote.rest

import com.transportation.bookcar.data.DEVICE_TYPE_GCM
import com.transportation.bookcar.data.remote.bean.*
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created on 2/2/2018.
 */
interface ApiClient {

    @Suppress("unused")
    @FormUrlEncoded
    @POST("push/device")
    fun registerDeviceToken(
            @Header("Authorization") token: String,
            @Field("deviceToken") deviceToken: String,
            @Field("deviceType") deviceType: String = DEVICE_TYPE_GCM
    ): Single<ApiRespond<Any>>

    @GET("movie/popular")
    fun getPopularMovie(
            @Query("api_key") token: String,
            @Query("page") page: Int
    ): Single<PageListDTO<MovieDTO>>

    @GET("tv/popular")
    fun getPopularTV(
            @Query("api_key") token: String,
            @Query("page") page: Int
    ): Single<PageListDTO<TvDTO>>

    @GET("maps/api/place/textsearch/json")
    fun getLocations(@Query("key") key: String,
                     @Query("query") query: String)
            : Single<LocationDTO>

    @GET("/maps/api/directions/json")
    fun getDirection(
            @Query("origin") origin: String,
            @Query("destination") destination: String,
            @Query("key") key: String,
            // Mode: driving(default), read more: https://developers.google.com/maps/documentation/directions/intro#TravelModes
            @Query("mode") mode: String?,
            // WayPoints: choose lat/lng or read more https://developers.google.com/maps/documentation/directions/intro#Waypoints
            @Query("waypoints") waypoints: String?,
            // Return more route but need more time
            @Query("alternatives") alternatives: Boolean,
            // read more: https://developers.google.com/maps/faq#languagesupport
            @Query("language") language: String?
    ): Single<PageListDTO<RouteDTO>>

    @GET("/maps/api/directions/json")
    fun getDirectionBetweenPlace(
            @Query("origin") origin: String,
            @Query("destination") destination: String,
            @Query("key") key: String,
            // Mode: driving(default), read more: https://developers.google.com/maps/documentation/directions/intro#TravelModes
            @Query("mode") mode: String?,
            // WayPoints: choose lat/lng or read more https://developers.google.com/maps/documentation/directions/intro#Waypoints
            @Query("waypoints") waypoints: String?,
            // Return more route but need more time
            @Query("alternatives") alternatives: Boolean,
            // read more: https://developers.google.com/maps/faq#languagesupport
            @Query("language") language: String?
    ): Single<DirectionDTO<RouteDTO>>
}
