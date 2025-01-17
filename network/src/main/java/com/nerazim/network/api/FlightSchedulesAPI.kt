package com.nerazim.network.api

import com.nerazim.network.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

//список будущих полетов (не позднее, чем через неделю)
interface FlightSchedulesAPI {

    //получение всех рейсов, вылетающих из данного аэропорта
    @GET("timetable")
    suspend fun getFlightsByDeparture(
        @Query("iataCode") departureCode: String,
        @Query("type") type: String = "departure",
        @Query("key") apiKey: String = BuildConfig.API_KEY
    )

    //получение всех рейсов, прибывающих в данный аэропорт
    @GET("flightsFuture")
    suspend fun getFlightsByArrival(
        @Query("iataCode") arrivalCode: String,
        @Query("type") type: String = "arrival",
        @Query("key") apiKey: String = BuildConfig.API_KEY
    )
}