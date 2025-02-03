package com.nerazim.flighttracker.screens

import android.app.Dialog
import android.content.SharedPreferences
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nerazim.flighttracker.R
import com.nerazim.flighttracker.util.toText
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import com.nerazim.network.util.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            setDateType = viewModel::setDateType,
            updateDate = viewModel::setDate,
            departure = departure?.airportName ?: "",
            arrival = arrival?.airportName ?: "",
            date = date?.toText() ?: ""
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
    setDateType: (type: Constants.ScheduleType) -> Unit,
    updateDate: (Long?) -> Unit,
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

        var choosingDate by rememberSaveable { mutableStateOf(false) }
        TextButton(
            modifier = Modifier.border(width = 2.dp, color = Color.Black),
            onClick = {
                choosingDate = true
            }
        ) {
            Text(
                text = date
            )
        }
        if (choosingDate) {
            FlightDatePicker(
                onDateSelected = { newDate ->
                    updateDate(newDate)
                },
                onDismiss = {
                    choosingDate = false
                },
                onSwitchChecked = { checked ->
                    if (checked) {
                        setDateType(Constants.ScheduleType.ARRIVAL)
                    }
                    else {
                        setDateType(Constants.ScheduleType.DEPARTURE)
                    }
                }
            )
        }
        Button(onClick = onNavigateToFlights) {
            Text(text = "Search")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    onSwitchChecked: (Boolean) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun RecentSearchesComponent() {

}