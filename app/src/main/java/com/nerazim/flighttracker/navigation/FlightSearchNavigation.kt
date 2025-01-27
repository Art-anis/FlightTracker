package com.nerazim.flighttracker.navigation

import android.content.SharedPreferences
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerazim.flighttracker.screens.FlightSearchScreen

//вершина навграфа - поиск рейсов
fun NavGraphBuilder.flightSearchDestination(
    pref: SharedPreferences,
    modifier: Modifier,
    onNavigateToLoading: () -> Unit,
    onNavigateToAirports: () -> Unit,
    onNavigateToFlights: () -> Unit
) {
    //экран поиска рейсов
    composable(Routes.FlightsSearch.route) {
        FlightSearchScreen(
            pref = pref,
            modifier = modifier,
            onNavigateToLoading = onNavigateToLoading,
            onNavigateToAirports = onNavigateToAirports,
            onNavigateToFlights = onNavigateToFlights
        )
    }
}
