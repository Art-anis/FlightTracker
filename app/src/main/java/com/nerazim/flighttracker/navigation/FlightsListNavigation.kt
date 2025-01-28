package com.nerazim.flighttracker.navigation

import androidx.navigation.NavController

//навигация к просмотру списка рейсов
fun NavController.onNavigateToFlights() {
    navigate(Routes.FlightsList.route)
}