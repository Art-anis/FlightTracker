package com.nerazim.network.di

import com.nerazim.network.api.AirportsAPI
import com.nerazim.network.api.CountriesAPI
import com.nerazim.network.api.FlightSchedulesAPI
import com.nerazim.network.api.FutureSchedulesAPI
import com.nerazim.network.util.Constants.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//базовый URL для ретрофита
const val baseUrl = BASE_URL

//создание инстанса ретрофита
fun provideRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

//создание всех API
fun provideAirportsAPI(retrofit: Retrofit): AirportsAPI {
    return retrofit.create(AirportsAPI::class.java)
}

fun provideCountriesAPI(retrofit: Retrofit): CountriesAPI {
    return retrofit.create(CountriesAPI::class.java)
}

fun provideFutureSchedulesAPI(retrofit: Retrofit): FutureSchedulesAPI {
    return retrofit.create(FutureSchedulesAPI::class.java)
}

fun provideFlightSchedulesAPI(retrofit: Retrofit): FlightSchedulesAPI {
    return retrofit.create(FlightSchedulesAPI::class.java)
}

//сетевой модуль для DI
val networkModule = module {
    //базовый URL
    single { baseUrl }

    //ретрофит
    single { provideRetrofit(baseUrl = get()) }

    //все API
    single { provideAirportsAPI(retrofit = get()) }
    single { provideCountriesAPI(retrofit = get()) }
    single { provideFutureSchedulesAPI(retrofit = get()) }
    single { provideFlightSchedulesAPI(retrofit = get()) }
}