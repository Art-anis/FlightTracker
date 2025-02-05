package com.nerazim.flighttracker.util

import com.nerazim.db.entities.AirportEntity
import com.nerazim.flighttracker.ui_models.AirportUIModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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