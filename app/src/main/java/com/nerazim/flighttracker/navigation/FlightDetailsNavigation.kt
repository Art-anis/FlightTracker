package com.nerazim.flighttracker.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerazim.flighttracker.screens.FlightDetailsScreen

//вершина навграфа - детали о рейсе
fun NavGraphBuilder.flightDetailsDestination(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    //экран просмотра отдельного рейса
    composable(Routes.FlightDetails.route) {
        FlightDetailsScreen(
            modifier = modifier,
            onNavigateBack = onNavigateBack
        )
    }
}

//навигация к просмотру деталей о рейсе
fun NavController.onNavigateToFlightDetails() {
    navigate(Routes.FlightDetails.route)
}