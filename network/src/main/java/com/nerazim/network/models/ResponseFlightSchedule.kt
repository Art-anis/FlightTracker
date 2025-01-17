package com.nerazim.network.models

import com.google.gson.annotations.SerializedName
import com.nerazim.network.models.schedule_subclasses.FlightScheduleEndpoint
import com.nerazim.network.models.schedule_subclasses.ScheduleAirline
import com.nerazim.network.models.schedule_subclasses.ScheduleCodeshared
import com.nerazim.network.models.schedule_subclasses.ScheduleFlight
import com.nerazim.network.util.Constants

//класс единицы расписания (менее, чем через неделю) для API
data class ResponseFlightSchedule(
    @SerializedName("airline") val airline: ScheduleAirline, //авиалиния
    @SerializedName("arrival") val arrival: FlightScheduleEndpoint, //пункт прибытия
    @SerializedName("codeshared") val codeshared: ScheduleCodeshared, //код-шеринг
    @SerializedName("departure") val departure: FlightScheduleEndpoint, //пункт отправления
    @SerializedName("flight") val flight: ScheduleFlight, //рейс
    @SerializedName("status") val status: Constants.FlightStatus, //статус рейса
    @SerializedName("type") val type: Constants.ScheduleType //тип единицы расписания (отправление/прибытие)
)
