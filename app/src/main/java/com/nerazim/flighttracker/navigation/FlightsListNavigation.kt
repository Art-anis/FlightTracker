package com.nerazim.flighttracker.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerazim.flighttracker.screens.FlightsListScreen

//вершина навграфа - список рейсов, результат поиска
fun NavGraphBuilder.flightsListDestination(
    modifier: Modifier,
    onNavigateToDetails: () -> Unit,
    onNavigateBack: () -> Unit
) {
    //экран просмотра списка рейсов
    composable(Routes.FlightsList.route) {
        FlightsListScreen(
            modifier = modifier,
            onNavigateToDetails = onNavigateToDetails,
            onNavigateBack = onNavigateBack
        )
    }
}

//навигация к просмотру списка рейсов
fun NavController.onNavigateToFlights() {
    navigate(Routes.FlightsList.route)
}