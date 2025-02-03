package com.nerazim.flighttracker.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nerazim.flighttracker.R
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.viewmodels.AirportSearchViewModel
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import com.nerazim.network.util.Constants
import org.koin.androidx.compose.koinViewModel
import java.util.Date

//экран поиска аэропорта, открывается после того, как пользователь решит выбрать аэропорт прилета или вылета
@Composable
fun AirportSearchScreen(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    flightSearchViewModel: FlightSearchViewModel
) {
    //личная viewmodel
    val airportViewModel = koinViewModel<AirportSearchViewModel>()
    //состояния
    var hint by rememberSaveable { mutableStateOf("") } //подсказка для поиска
    val airports by airportViewModel.airports.observeAsState(listOf()) //список аэропортов, подписываемся на livedata в viewmodel
    val history by airportViewModel.history.observeAsState(listOf()) //история поиска аэропортов
    //получение истории из БД
    airportViewModel.getHistory()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.teal_700)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //кнопка "Назад"
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            //поле поиска
            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(0.8f),
                value = hint,
                //закругленные углы
                shape = RoundedCornerShape(size = 20.dp),
                //убираем индикатор
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                //иконка поиска
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                //плейсхолдер
                placeholder = {
                    Text(
                        text = stringResource(R.string.airports_search_placeholder)
                    )
                },
                //обновление значения и запуск поиска
                onValueChange = {
                    hint = it
                    airportViewModel.getAirports(hint)
                }
            )
        }
        //столбец с результатами поиска
        LazyColumn(
            modifier = modifier.fillMaxWidth()
        ) {
            airportList(
                list = history,
                onNavigateBack = onNavigateBack,
                viewModel = flightSearchViewModel,
                selectAirport = airportViewModel::selectAirport,
                title = R.string.airports_history_result
            )
            //разделитель между двумя списками
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            airportList(
                list = airports,
                onNavigateBack = onNavigateBack,
                viewModel = flightSearchViewModel,
                selectAirport = airportViewModel::selectAirport,
                title = R.string.airports_search_result
            )
        }
    }
}

//список аэропортов
fun LazyListScope.airportList(
    list: List<AirportUIModel>,
    onNavigateBack: () -> Unit,
    selectAirport: (name: String, time: Long) -> Unit,
    viewModel: FlightSearchViewModel,
    @StringRes title: Int
) {
    //заголовок
    if (list.isNotEmpty()) {
        item {
            Text(
                text = stringResource(title)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    //сам список
    itemsIndexed(list) { index, airport ->
        var selectedItem by rememberSaveable { mutableStateOf("") }
        AirportItem(
            airport = airport,
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = selectedItem == airport.airportName,
                    onClick = {
                        //при нажатии отмечаем элемент как выбранный
                        selectedItem = airport.airportName
                        //записываем данные о выбранном аэропорте в соответствующее поле viewmodel
                        if (viewModel.currentAirportType == Constants.ScheduleType.DEPARTURE) {
                            viewModel.setDepartureAirport(airport)
                        }
                        else {
                            viewModel.setArrivalAirport(airport)
                        }
                        selectAirport(
                            airport.airportName,
                            Date().time
                        )
                        //перемещаемся назад
                        onNavigateBack()
                    }
                )
        )
        //divider между элементами не должен отображаться после последнего элемента списка
        if (index < list.lastIndex) {
            HorizontalDivider(color = Color.Black)
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