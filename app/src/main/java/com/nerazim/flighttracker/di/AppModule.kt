package com.nerazim.flighttracker.di

import com.nerazim.flighttracker.data_loading.AirportLoader
import com.nerazim.flighttracker.data_loading.CityLoader
import org.koin.dsl.module

//модуль приложения для DI
val appModule = module {
    //загрузчики данных
    single { CityLoader() }
    single { AirportLoader() }
}