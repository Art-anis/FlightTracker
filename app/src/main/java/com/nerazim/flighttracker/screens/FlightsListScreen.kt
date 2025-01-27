package com.nerazim.flighttracker.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//экран просмотра рейсов, открывается в результате поиска
@Composable
fun FlightsListScreen(
    modifier: Modifier,
    onNavigateToDetails: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Text("Flights list screen")
}