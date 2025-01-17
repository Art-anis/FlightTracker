package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс код-шеринга
data class ScheduleCodeshared(
    @SerializedName("airline") val airline: ScheduleAirline, //вспомогательная авиалиния
    @SerializedName("flight") val flight: ScheduleFlight //вспомогательный рейс
)
