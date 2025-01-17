package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс авиалинии
data class ScheduleAirline(
    @SerializedName("name") val airlineName: String, //название
    @SerializedName("iataCode") val iata: String, //код iata
    @SerializedName("icaoCode") val icao: String //код icao
)
