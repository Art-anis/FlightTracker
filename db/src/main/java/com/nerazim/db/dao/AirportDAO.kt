package com.nerazim.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerazim.db.entities.AirportEntity

//DAO аэропортов
@Dao
interface AirportDAO {

    //добавление аэропорта
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAirport(airport: AirportEntity)

    //получение случайного аэропорта для первоначальной проверки
    @Query("SELECT * FROM airports ORDER BY random() LIMIT 1")
    fun getRandomAirport(): AirportEntity?

    //поиск аэропорта по подсказке
    @Query("SELECT * FROM airports WHERE airport_name LIKE :hint")
    suspend fun searchAirports(hint: String): List<AirportEntity>
}