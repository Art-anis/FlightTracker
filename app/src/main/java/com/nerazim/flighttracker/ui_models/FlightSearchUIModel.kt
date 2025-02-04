package com.nerazim.flighttracker.ui_models

import java.util.Date

data class FlightSearchUIModel(
    val departure: AirportUIModel = AirportUIModel(),
    val arrival: AirportUIModel = AirportUIModel(),
    val date: Date = Date()
)
