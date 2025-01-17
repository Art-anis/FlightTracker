package com.nerazim.network.api

import com.nerazim.network.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

//API будущих полетов (через неделю и позднее)
interface FutureSchedulesAPI {

    //получение всех рейсов, вылетающих из данного аэропорта в данный день
    @GET("flightsFuture")
    suspend fun getFlightsByDeparture(
        @Query("iataCode") departureCode: String,
        @Query("date") date: String, //YYYY-MM-DD формат, дата вылета
        @Query("type") type: String = "departure",
        @Query("key") apiKey: String = BuildConfig.API_KEY
    )

    //получение всех рейсов, прибывающих в данный аэропорт в данный день
    @GET("flightsFuture")
    suspend fun getFlightsByArrival(
        @Query("iataCode") arrivalCode: String,
        @Query("date") date: String, //YYYY-MM-DD формат, дата прибытия
        @Query("type") type: String = "arrival",
        @Query("key") apiKey: String = BuildConfig.API_KEY
    )
}