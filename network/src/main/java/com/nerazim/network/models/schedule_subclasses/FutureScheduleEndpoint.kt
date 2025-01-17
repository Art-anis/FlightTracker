package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс пункта отправления/прибытия для FutureFlight
data class FutureScheduleEndpoint(
    @SerializedName("iataCode") val iata: String, //код iata
    @SerializedName("icaoCode") val icao: String, //код icao
    @SerializedName("terminal") val terminal: String, //номер/название терминала
    @SerializedName("gate") val gate: String, //номер выхода
    @SerializedName("scheduledTime") val scheduledTime: String //время отправки/прибытия по расписанию
)
