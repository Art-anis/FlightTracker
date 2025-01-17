package com.nerazim.network.api

import com.nerazim.network.BuildConfig
import com.nerazim.network.models.ResponseCountry
import retrofit2.http.GET
import retrofit2.http.Query

//API стран
interface CountriesAPI {

    //получить все страны
    @GET("countryDatabase")
    suspend fun getAllCountries(
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): List<ResponseCountry>

    //получить одиночную страну по ее ISO2 коду
    @GET("countryDatabase")
    suspend fun getCountryByCode(
        @Query("codeIso2Country") countryCode: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): ResponseCountry
}