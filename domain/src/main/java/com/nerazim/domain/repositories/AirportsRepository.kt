package com.nerazim.domain.repositories

import com.nerazim.db.dao.AirportDAO
import com.nerazim.db.entities.AirportEntity

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
}