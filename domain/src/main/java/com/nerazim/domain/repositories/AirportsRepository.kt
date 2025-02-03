package com.nerazim.domain.repositories

import com.nerazim.db.dao.AirportDAO
import com.nerazim.db.entities.AirportEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//репозиторий для аэропортов
class AirportsRepository(
    private val dao: AirportDAO //локальный источник данных
) {
    //поиск по базе данных по названию аэропортов
    fun getAirportsFromDb(hint: String) = dao.searchAirports(hint = "$hint%")

    //добавить аэропорт
    suspend fun addAirport(airport: AirportEntity) {
        dao.addAirport(airport)
    }

    //выбор аэропорта и попутное обновление времени поиска
    suspend fun selectAirport(name: String, time: Long) {
        withContext(Dispatchers.IO) {
            dao.selectAirport(name = name, searchTime = time)
        }
    }

    //получение истории
    suspend fun getHistory() = dao.getLastSearchedAirports()
}