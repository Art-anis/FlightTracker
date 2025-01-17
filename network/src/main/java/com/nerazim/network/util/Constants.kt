package com.nerazim.network.util

//константы сетевого модуля
object Constants {
    //базовый URL
    const val BASE_URL = "https://aviation-edge.com/v2/public/"

    //статус рейса
    enum class FlightStatus(value: String) {
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
    enum class ScheduleType(value: String) {
        DEPARTURE("departure"),
        ARRIVAL("arrival")
    }
}