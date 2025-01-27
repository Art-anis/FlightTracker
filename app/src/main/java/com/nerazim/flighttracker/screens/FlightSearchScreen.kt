package com.nerazim.flighttracker.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nerazim.flighttracker.R

//экран поиска рейсов, первый экран, который видит пользователь (если нет загрузки данных)
@Composable
fun FlightSearchScreen(
    pref: SharedPreferences,
    modifier: Modifier,
    onNavigateToLoading: () -> Unit,
    onNavigateToAirports: () -> Unit,
    onNavigateToFlights: () -> Unit
) {
    val firstLaunch = pref.getBoolean(stringResource(R.string.first_launch), false)
    if (firstLaunch) {
        onNavigateToLoading()
    }
    //TODO: сделать экран
    Column {
        Text("Flights search screen")
        Row {
            Button(
                onClick = onNavigateToAirports
            ) {
                Text("Airports")
            }
            Button(
                onClick = onNavigateToFlights
            ) {
                Text("Flights")
            }
        }
    }

}