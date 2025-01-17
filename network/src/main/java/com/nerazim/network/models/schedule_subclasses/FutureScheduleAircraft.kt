package com.nerazim.network.models.schedule_subclasses

import com.google.gson.annotations.SerializedName

//класс самолета для FutureFlight
data class FutureScheduleAircraft(
    @SerializedName("modelCode") val modelCode: String, //код модели
    @SerializedName("modelText") val modelName: String //название модели
)
