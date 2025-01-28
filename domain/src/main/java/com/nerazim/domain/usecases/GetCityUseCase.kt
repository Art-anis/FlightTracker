package com.nerazim.domain.usecases

import com.nerazim.db.entities.CityEntity
import com.nerazim.domain.repositories.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCityUseCase(private val repository: CitiesRepository) {
    suspend fun execute(iata: String): CityEntity {
        return withContext(Dispatchers.IO) {
            repository.getCityByIata(iata)
        }
    }
}