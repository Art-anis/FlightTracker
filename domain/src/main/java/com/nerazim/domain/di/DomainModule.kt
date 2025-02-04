package com.nerazim.domain.di

import com.nerazim.domain.repositories.AirportsRepository
import com.nerazim.domain.repositories.CitiesRepository
import com.nerazim.domain.repositories.FlightsRepository
import org.koin.dsl.module

//модуль домена - репозитории
val domainModule = module {
    single { CitiesRepository(dao = get()) }
    single { AirportsRepository(dao = get()) }
    single { FlightsRepository(historyDao = get(), schedulesAPI = get(), futureSchedulesAPI = get()) }
}