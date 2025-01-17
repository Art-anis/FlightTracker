package com.nerazim.db.di

import android.content.Context
import androidx.room.Room
import com.nerazim.db.dao.AirportDAO
import com.nerazim.db.dao.CityDAO
import com.nerazim.db.database.AppDatabase
import org.koin.dsl.module

//сборка базы данных
fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "flight_tracker_db"
        ).build()
}

//предоставление DAO аэропортов
fun provideAirportDao(db: AppDatabase): AirportDAO {
    return db.airportDao()
}

//предоставление DAO городов
fun provideCityDao(db: AppDatabase): CityDAO {
    return db.cityDao()
}

//модуль БД для DI
val databaseModule = module {
    //инстанс БД
    single<AppDatabase>(createdAtStart = true) { provideDatabase(context = get()) }

    //DAO
    single { provideAirportDao(db = get()) }
    single { provideCityDao(db = get()) }
}
