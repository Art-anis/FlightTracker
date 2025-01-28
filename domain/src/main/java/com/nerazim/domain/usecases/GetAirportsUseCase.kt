package com.nerazim.domain.usecases

import com.nerazim.db.entities.AirportEntity
import com.nerazim.domain.repositories.AirportsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAirportsUseCase(private val repository: AirportsRepository) {
    suspend fun execute(hint: String): Flow<List<AirportEntity>> {
        return withContext(Dispatchers.IO) {
            repository.getAirportsFromDb(hint = hint)
        }
    }
}