package com.nerazim.flighttracker.util

import com.nerazim.db.entities.AirportEntity
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.network.models.ResponseAirport

//конвертирование модели для API в модель для БД
fun ResponseAirport.toAirportEntity(): AirportEntity {
    return AirportEntity(
        name = this.name,
        icao = this.icao ?: "empty",
        iata = this.iata,
        cityIata = this.cityIata ?: "empty",
        countryName = this.countryName ?: "empty"
    )
}

fun AirportEntity.toAirportUIModel(cityName: String): AirportUIModel {
    return AirportUIModel(
        airportName = this.name,
        iata = this.iata,
        cityName = cityName,
        countryName = this.countryName
    )
}