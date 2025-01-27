package com.nerazim.flighttracker.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//экран поиска аэропорта, открывается после того, как пользователь решит выбрать аэропорт прилета или вылета
@Composable
fun AirportSearchScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit
) {
    Text("Airport search screen")
}