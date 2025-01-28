package com.nerazim.flighttracker.navigation

import androidx.navigation.NavController

//навигация к выбору аэропорта
fun NavController.onNavigateToAirports() {
    navigate(Routes.AirportSearch().route)
}