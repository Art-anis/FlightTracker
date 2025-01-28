package com.nerazim.domain.repositories

import com.nerazim.db.dao.CityDAO

class CitiesRepository(private val dao: CityDAO) {
    suspend fun getCityByIata(iata: String) = dao.getCityByIATA(iata)
}