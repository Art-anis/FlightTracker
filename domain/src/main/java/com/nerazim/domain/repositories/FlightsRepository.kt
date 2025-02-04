package com.nerazim.domain.repositories

import com.nerazim.db.dao.FlightSearchHistoryDAO
import com.nerazim.db.entities.FlightSearchHistory
import com.nerazim.network.api.FlightSchedulesAPI
import com.nerazim.network.api.FutureSchedulesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//репозиторий для рейсов
class FlightsRepository(
    private val historyDao: FlightSearchHistoryDAO, //dao истории поиска
    private val schedulesAPI: FlightSchedulesAPI, //api для рейсов менее, чем через неделю
    private val futureSchedulesAPI: FutureSchedulesAPI //api для рейсов более, чем через неделю
) {
    //получение истории поиска рейсов
    suspend fun getHistory(): List<FlightSearchHistory> {
        return withContext(Dispatchers.IO) {
            historyDao.getHistory()
        }
    }

    suspend fun addFlightHistory(flight: FlightSearchHistory) {
        withContext(Dispatchers.IO) {
            historyDao.addFlightItem(flight = flight)
        }
    }

    //получение вылетов из заданного аэропорта менее, чем через неделю
    suspend fun getNearDepartures(departure: String)
        = schedulesAPI.getFlightsByDeparture(departureCode = departure)

    //получение вылетов из заданного аэропорта более, чем через неделю, в заданный день
    suspend fun getFarDepartures(departure: String, date: String)
        = futureSchedulesAPI.getFlightsByDeparture(departureCode = departure, date = date)
}