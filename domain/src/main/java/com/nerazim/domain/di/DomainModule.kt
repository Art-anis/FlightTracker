package com.nerazim.domain.di

import com.nerazim.domain.repositories.AirportsRepository
import com.nerazim.domain.repositories.CitiesRepository
import com.nerazim.domain.usecases.GetAirportsUseCase
import com.nerazim.domain.usecases.GetCityUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CitiesRepository(dao = get()) }
    single { AirportsRepository(dao = get()) }

    factory { GetCityUseCase(repository = get()) }
    factory { GetAirportsUseCase(repository = get()) }
}