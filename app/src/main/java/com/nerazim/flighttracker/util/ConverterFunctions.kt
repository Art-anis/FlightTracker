package com.nerazim.flighttracker.util

import com.nerazim.db.entities.AirportEntity
import com.nerazim.db.entities.FlightSearchHistory
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.ui_models.FlightSearchUIModel
import com.nerazim.network.models.ResponseAirport
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

//конвертирование модели БД в модель для UI
fun AirportEntity.toAirportUIModel(cityName: String): AirportUIModel {
    return AirportUIModel(
        airportName = this.name,
        iata = this.iata,
        cityName = cityName,
        countryName = this.countryName
    )
}

//конвертирование даты в текст
fun Date.toText(): String {
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(this)
}