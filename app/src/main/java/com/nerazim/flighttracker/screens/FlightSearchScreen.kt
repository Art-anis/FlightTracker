package com.nerazim.flighttracker.screens

import android.content.SharedPreferences
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nerazim.flighttracker.R
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import com.nerazim.network.util.Constants

//экран поиска рейсов, первый экран, который видит пользователь (если нет загрузки данных)
@Composable
fun FlightSearchScreen(
    pref: SharedPreferences,
    modifier: Modifier,
    onNavigateToLoading: () -> Unit,
    onNavigateToAirports: (type: Constants.ScheduleType) -> Unit,
    onNavigateToFlights: () -> Unit,
    viewModel: FlightSearchViewModel
) {
    val firstLaunch = pref.getBoolean(stringResource(R.string.first_launch), false)
    if (firstLaunch) {
        onNavigateToLoading()
    }

    //состояния
    val departure by viewModel.departure.observeAsState() //вылет
    val arrival by viewModel.arrival.observeAsState() //прибытие
    val date by viewModel.date.observeAsState() //дата

    Column {
        //компонент для поиска
        SearchComponent(
            onNavigateToAirports = onNavigateToAirports,
            onNavigateToFlights = onNavigateToFlights,
            departure = departure?.airportName ?: "",
            arrival = arrival?.airportName ?: "",
            date = date?.toString() ?: ""
        )
        //компонент истории поиска
        RecentSearchesComponent()
    }
}

//компонент поиска
@Composable
fun SearchComponent(
    onNavigateToAirports: (type: Constants.ScheduleType) -> Unit,
    onNavigateToFlights: () -> Unit,
    departure: String,
    arrival: String,
    date: String
) {
    Column {
        TextButton(
            modifier = Modifier.border(width = 2.dp, color = Color.Black),
            onClick = {
                onNavigateToAirports(Constants.ScheduleType.DEPARTURE)
            }
        ) {
            Text(
                text = departure
            )
        }
        TextButton(
            modifier = Modifier.border(width = 2.dp, color = Color.Black),
            onClick = {
                onNavigateToAirports(Constants.ScheduleType.ARRIVAL)
            }
        ) {
            Text(
                text = arrival
            )
        }
        Text(
            modifier = Modifier.border(width = 2.dp, color = Color.Black),
            text = date
        )
        Button(onClick = onNavigateToFlights) {
            Text(text = "Search")
        }
    }
}

@Composable
fun RecentSearchesComponent() {

}