package com.nerazim.flighttracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerazim.db.entities.FlightSearchHistory
import com.nerazim.domain.repositories.FlightsRepository
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.ui_models.FlightSearchUIModel
import com.nerazim.network.util.Constants
import kotlinx.coroutines.launch
import java.util.Date

//viewmodel поиска рейсов
class FlightSearchViewModel(
    private val repository: FlightsRepository
): ViewModel() {

    //состояние объекта поиска
    private var _searchState: MutableLiveData<FlightSearchUIModel> = MutableLiveData(FlightSearchUIModel())
    val searchState: LiveData<FlightSearchUIModel>
        get() = _searchState

    //тип выбираемой даты: дата вылета или дата прибытия
    private var _dateType: MutableLiveData<Constants.ScheduleType> = MutableLiveData(Constants.ScheduleType.DEPARTURE)
    val dateType: LiveData<Constants.ScheduleType>
        get() = _dateType

    //тип аэропорта: прибытие/вылет
    private var _currentAirportType: Constants.ScheduleType = Constants.ScheduleType.DEPARTURE
    val currentAirportType: Constants.ScheduleType
        get() = _currentAirportType

    //история поиска рейсов
    private var _history: MutableLiveData<List<FlightSearchHistory>> = MutableLiveData()
    val history: LiveData<List<FlightSearchHistory>>
        get() = _history

    init {
        getHistory()
    }

    //получение истории поиска из БД
    private fun getHistory() {
        viewModelScope.launch {
            //получаем историю и маппим ее в UI-модели
            _history.value = repository
                .getHistory()
        }
    }

    //добавление элемента в историю поиска
    fun addToHistory() {
        viewModelScope.launch {
            repository.addFlightHistory(
                flight = FlightSearchHistory(
                    departure = _searchState.value?.departure?.airportName ?: "",
                    arrival = _searchState.value?.arrival?.airportName ?: ""
                )
            )
            getHistory()
        }
    }

    //обновление аэропорта вылета
    fun setDepartureAirport(airport: AirportUIModel) {
        _searchState.value = _searchState.value?.copy(
            departure = airport
        )
    }

    //обновление аэропорта прибытия
    fun setArrivalAirport(airport: AirportUIModel) {
        _searchState.value = _searchState.value?.copy(
            arrival = airport
        )
    }

    //обновление типа аэропорта (прибытие/вылет)
    fun setAirportType(type: Constants.ScheduleType) {
        _currentAirportType = type
    }

    //обновление даты
    fun setDate(date: Long?) {
        date?.let {
            _searchState.value = _searchState.value?.copy(
                date = Date(date)
            )
        }
    }

    //обновление типа даты: вылет/прибытие
    fun setDateType(type: Constants.ScheduleType) {
        _dateType.value = type
    }
}