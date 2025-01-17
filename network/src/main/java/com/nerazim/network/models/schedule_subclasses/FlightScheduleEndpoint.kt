package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс пункта отправления/прибытия для рейса через неделю или менее
data class FlightScheduleEndpoint(
    @SerializedName("actualRunway") val actualRunway: String, //фактическое время взлета/посадки
    @SerializedName("actualTime") val actualTime: String, //фактическое время начала/конца движения самолета
    @SerializedName("baggage") val baggageBelt: String, //багажная лента (для прибытий)
    @SerializedName("delay") val minutesDelayed: String, //задержка в минутах
    @SerializedName("estimatedRunway") val estimatedRunway: String, //ожидаемое время взлета/посадки
    @SerializedName("estimatedTime") val estimatedTime: String, //ожидаемое время начала/конца движения самолета
    @SerializedName("gate") val gateNumber: String, //номер выхода
    @SerializedName("iataCode") val iata: String, //код iata
    @SerializedName("icaoCode") val icao: String, //код icao
    @SerializedName("scheduledTime") val scheduledTime: String, //время отправки/прибытия по расписанию
    @SerializedName("terminal") val terminal: String //номер/название терминала
)
