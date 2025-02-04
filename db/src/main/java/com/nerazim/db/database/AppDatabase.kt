package com.nerazim.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nerazim.db.dao.AirportDAO
import com.nerazim.db.dao.CityDAO
import com.nerazim.db.dao.FlightSearchHistoryDAO
import com.nerazim.db.entities.AirportEntity
import com.nerazim.db.entities.CityEntity
import com.nerazim.db.entities.FlightSearchHistory

//база данных
@Database(
    entities = [AirportEntity::class, CityEntity::class, FlightSearchHistory::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    //объявление всех DAO, которые Room соберет
    abstract fun airportDao(): AirportDAO
    abstract fun cityDao(): CityDAO
    abstract fun flightSearchHistoryDao(): FlightSearchHistoryDAO
}