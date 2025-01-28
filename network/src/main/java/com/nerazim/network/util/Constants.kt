package com.nerazim.network.util

import kotlinx.serialization.Serializable

//константы сетевого модуля
object Constants {
    //базовый URL
    const val BASE_URL = "https://aviation-edge.com/v2/public/"

    //статус рейса
    enum class FlightStatus(val value: String) {
        LANDED("landed"),
        SCHEDULED("scheduled"),
        CANCELLED("cancelled"),
        ACTIVE("active"),
        INCIDENT("incident"),
        DIVERTED("diverted"),
        REDIRECTED("redirected"),
        UNKNOWN("unknown")
    }

    //тип единицы расписания
    @Serializable
    enum class ScheduleType(val value: String) {
        DEPARTURE("departure"),
        ARRIVAL("arrival")
    }
}