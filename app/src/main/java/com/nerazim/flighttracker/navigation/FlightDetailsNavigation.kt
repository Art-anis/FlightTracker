package com.nerazim.flighttracker.navigation

import androidx.navigation.NavController

//навигация к просмотру деталей о рейсе
fun NavController.onNavigateToFlightDetails() {
    navigate(Routes.FlightDetails.route)
}