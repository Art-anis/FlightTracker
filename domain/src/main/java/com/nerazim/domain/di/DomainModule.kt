package com.nerazim.domain.di

import com.nerazim.domain.repositories.AirportsRepository
import com.nerazim.domain.repositories.CitiesRepository
import org.koin.dsl.module

val domainModule = module {
    single { CitiesRepository(dao = get()) }
    single { AirportsRepository(dao = get()) }
}