package com.nerazim.flighttracker.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//экран просмотра рейса, открывается при нажатии на соответствующий компонент
@Composable
fun FlightDetailsScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    Text("Flight details screen")
}