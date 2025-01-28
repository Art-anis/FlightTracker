package com.nerazim.flighttracker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import com.nerazim.network.util.Constants

//экран поиска аэропорта, открывается после того, как пользователь решит выбрать аэропорт прилета или вылета
@Composable
fun AirportSearchScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    viewModel: FlightSearchViewModel
) {
    //состояния
    var hint by rememberSaveable { mutableStateOf("") } //подсказка для поиска
    val airports by viewModel.airports.observeAsState(listOf()) //список аэропортов, подписываемся на livedata в viewmodel

    Column {
        Row {
            //кнопка "Назад"
            IconButton(
                onClick = onNavigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            //поле поиска
            TextField(
                value = hint,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    hint = it
                    viewModel.getAirports(hint)
                }
            )
        }
        var selectedItem by remember { mutableStateOf("") }
        //столбец с результатами поиска
        LazyColumn {
            itemsIndexed(airports) { index, airport ->
                AirportItem(
                    airport = airport,
                    modifier = Modifier.selectable(
                        selected = selectedItem == airport.airportName,
                        onClick = {
                            selectedItem = airport.airportName
                            if (viewModel.currentAirportType == Constants.ScheduleType.DEPARTURE) {
                                viewModel.setDepartureAirport(airport)
                            }
                            else {
                                viewModel.setArrivalAirport(airport)
                            }
                            onNavigateBack()
                        }
                    )
                )
                //divider между элементами не должен отображаться после последнего элемента списка
                if (index < airports.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}

//элемент списка аэропортов
@Composable
fun AirportItem(
    airport: AirportUIModel,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        //общая информация - название страны и города
        Text(
            text = "${airport.cityName}, ${airport.countryName}"
        )
        //детальная информация - название и код аэропорта
        Text(
            text = "${airport.iata} - ${airport.airportName}"
        )
    }
}