package com.nerazim.flighttracker.screens

import android.app.Dialog
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
            departure = departure?.airportName ?: stringResource(R.string.departure_placeholder),
            arrival = arrival?.airportName ?: stringResource(R.string.arrival_placeholder),
            date = stringResource(R.string.date_header) + date?.toText()
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
    //общий контейнер
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.teal_700))
    ) {
        //отступ сверху
        Spacer(modifier = Modifier.height(16.dp))
        //текст для точки вылета
        Text(
            text = departure,
            modifier = Modifier
                .padding(horizontal = 16.dp) //margin
                .clip(RoundedCornerShape(10.dp)) //обрезанные углы
                .background(color = Color.White) //фон белый
                .padding(horizontal = 8.dp) //padding
                .fillMaxWidth() //заполняем все доступное место
                .clickable { //делаем его кликабельным
                    onNavigateToAirports(Constants.ScheduleType.DEPARTURE)
                },
        )
        Spacer(modifier = Modifier.height(4.dp))
        //текст для точки прибытия
        Text(
            text = arrival,
            modifier = Modifier
                .padding(horizontal = 16.dp) //margin
                .clip(RoundedCornerShape(10.dp)) //обрезанные углы
                .background(color = Color.White) //фон белый
                .padding(horizontal = 8.dp) //padding
                .fillMaxWidth() //заполняем все доступное место
                .clickable { //делаем его кликабельным
                    onNavigateToAirports(Constants.ScheduleType.ARRIVAL)
                }
        )
        //отступ между местами и датой
        Spacer(modifier = Modifier.height(8.dp))

        //состояние для диалога выбора дат
        var choosingDate by rememberSaveable { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var departureChecked by rememberSaveable { mutableStateOf(true) }
            var arrivalChecked by rememberSaveable { mutableStateOf(false) }

            //текст для даты
            Text(
                text = date,
                modifier = Modifier
                    .padding(horizontal = 16.dp) //margin
                    .clip(RoundedCornerShape(10.dp)) //обрезанные углы
                    .background(color = Color.White) //фон белый
                    .padding(horizontal = 8.dp) //padding
                    .fillMaxWidth(0.4f) //заполняем часть свободного места
                    .clickable { //делаем его кликабельным
                        choosingDate = true
                    }
            )
            //чекбокс для вылета
            Text(
                text = stringResource(R.string.departure)
            )
            Checkbox(
                checked = departureChecked,
                onCheckedChange = { value ->
                    departureChecked = value
                    arrivalChecked = !value
                    setDateType(Constants.ScheduleType.DEPARTURE)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.on_teal)
                )
            )
            //чекбокс для прибытия
            Text(
                text = stringResource(R.string.arrival)
            )
            Checkbox(
                checked = arrivalChecked,
                onCheckedChange = { value ->
                    arrivalChecked = value
                    departureChecked = !value
                    setDateType(Constants.ScheduleType.ARRIVAL)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(R.color.on_teal)
                )
            )
        }
        //если мы выбираем дату, то открываем диалог
        if (choosingDate) {
            FlightDatePicker(
                onDateSelected = { newDate ->
                    updateDate(newDate)
                },
                onDismiss = {
                    choosingDate = false
                }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.75f),
                onClick = onNavigateToFlights,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.on_teal)
                )
            ) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

//компонент выбора даты
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    //состояние диалога
    val datePickerState = rememberDatePickerState()

    //сам диалог
    DatePickerDialog(
        //при закрытии диалога
        onDismissRequest = {
            onDismiss()
        },
        //кнопка подтверждения
        confirmButton = {
            TextButton(onClick = {
                //при нажатии обновляем дату и закрываем диалог
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        //кнопка отмены
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    ) {
        //календарь
        DatePicker(state = datePickerState)
    }
}

@Composable
fun RecentSearchesComponent() {

}