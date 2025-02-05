package com.nerazim.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerazim.db.entities.AirportEntity
import com.nerazim.db.entities.FlightSearchHistory

//dao для истории поиска рейсов
@Dao
interface FlightSearchHistoryDAO {

    //добавление нового рейса в историю поиска
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFlightItem(flight: FlightSearchHistory)

    //получение последних 3 рейсов из истории поиска
    @Query("SELECT * FROM flight_search_history ORDER BY id DESC LIMIT 3")
    suspend fun getHistory(): List<FlightSearchHistory>

    //получение аэропорта по его названию
    @Query("SELECT * FROM airports WHERE airport_name = :name")
    suspend fun getAirportByName(name: String): AirportEntity
}