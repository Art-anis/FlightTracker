package com.nerazim.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerazim.db.entities.CityEntity

//DAO городов
@Dao
interface CityDAO {

    //добавление нового города
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityEntity)

    //получение случайного города для первоначальной проверки
    @Query("SELECT * FROM cities ORDER BY random() LIMIT 1")
    fun getRandomCity(): CityEntity?

    //получение города по его IATA
    @Query("SELECT * FROM cities WHERE iata_code = :iata")
    suspend fun getCityByIATA(iata: String): CityEntity
}