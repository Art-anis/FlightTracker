package com.nerazim.network.models

import com.google.gson.annotations.SerializedName
import com.nerazim.network.models.schedule_subclasses.*

//класс единицы будущего расписания (через неделю и далее) для API
data class ResponseFutureSchedule(
    @SerializedName("weekday") val weekday: String, //день недели
    @SerializedName("departure") val departure: FutureScheduleEndpoint, //пункт отправления
    @SerializedName("arrival") val arrival: FutureScheduleEndpoint, //пункт прибытия
    @SerializedName("aircraft") val aircraft: FutureScheduleAircraft, //самолет
    @SerializedName("airline") val airline: ScheduleAirline, //авиалиния
    @SerializedName("flight") val flight: ScheduleFlight, //рейс
    @SerializedName("codeshared") val codeshared: ScheduleCodeshared //код-шеринг
)
