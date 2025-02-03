package com.nerazim.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerazim.db.entities.AirportEntity
import kotlinx.coroutines.flow.Flow

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
    @Query("SELECT * FROM airports WHERE airport_name LIKE :hint OR iata_code LIKE :hint")
    fun searchAirports(hint: String): Flow<List<AirportEntity>>

    //обновление времени поиска аэропорта
    @Query("UPDATE airports SET last_search = :searchTime WHERE airport_name = :name")
    fun selectAirport(name: String, searchTime: Long)

    //получение истории поиска
    @Query("SELECT * FROM airports ORDER BY last_search DESC LIMIT 3")
    suspend fun getLastSearchedAirports(): List<AirportEntity>
}