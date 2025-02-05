package com.nerazim.flighttracker.di

import com.nerazim.domain.data_loading.AirportLoader
import com.nerazim.domain.data_loading.CityLoader
import com.nerazim.flighttracker.viewmodels.AirportSearchViewModel
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//модуль приложения для DI
val appModule = module {
    //загрузчики данных
    single { CityLoader() }
    single { AirportLoader() }

    //viewmodel для поиска рейсов
    viewModel<FlightSearchViewModel> {
        FlightSearchViewModel(
            repository = get()
        )
    }

    //viewmodel для поиска аэропортов
    viewModel<AirportSearchViewModel> {
        AirportSearchViewModel(
            airportsRepository = get(),
            citiesRepository = get()
        )
    }
}