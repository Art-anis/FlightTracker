package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс рейса
data class ScheduleFlight(
    @SerializedName("number") val flightNumber: String, //номер рейса
    @SerializedName("iataNumber") val iata: String, //код iata
    @SerializedName("icaoNumber") val icao: String //код icao
)
