package com.nerazim.network.api

import com.nerazim.network.BuildConfig
import com.nerazim.network.models.ResponseAirport
import retrofit2.http.GET
import retrofit2.http.Query

//API аэропортов
interface AirportsAPI {
    //получить все аэропорты страны по ее коду
    @GET("airportDatabase")
    suspend fun getAirportsByCountry(
        @Query("codeIso2Country") countryCode: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY

    ): List<ResponseAirport>

    //получить одиночный аэропорт по его IATA коду
    @GET("airportDatabase")
    suspend fun getAirportByCode(
        @Query("codeIataAirport") airportCode: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): ResponseAirport

    //получить все аэропорты
    @GET("airportDatabase")
    suspend fun getAllAirports(
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): List<ResponseAirport>
}