package com.nerazim.flighttracker.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerazim.flighttracker.screens.AirportSearchScreen

//вершина навграфа - поиск аэропорта
fun NavGraphBuilder.airportSearchDestination(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
) {
    //экран поиска аэропорта
    composable(Routes.AirportSearch.route) {
        AirportSearchScreen(
            modifier = modifier,
            onNavigateBack = onNavigateBack
        )
    }
}

//навигация к выбору аэропорта
fun NavController.onNavigateToAirports() {
    navigate(Routes.AirportSearch.route)
}