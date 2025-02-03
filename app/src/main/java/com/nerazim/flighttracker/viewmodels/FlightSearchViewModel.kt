package com.nerazim.flighttracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.network.util.Constants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

//viewmodel поиска рейсов
class FlightSearchViewModel: ViewModel() {

    //пункт вылета
    private val _departure: MutableLiveData<AirportUIModel> = MutableLiveData()
    val departure: LiveData<AirportUIModel> = _departure

    //пункт прибытия
    private val _arrival: MutableLiveData<AirportUIModel> = MutableLiveData()
    val arrival: LiveData<AirportUIModel> = _arrival

    //дата вылета/прибытия
    private var _date: MutableLiveData<Date> = MutableLiveData(Date())
    val date: LiveData<Date>
        get() = _date

    //тип выбираемой даты: дата вылета или дата прибытия
    private var _dateType: MutableLiveData<Constants.ScheduleType> = MutableLiveData(Constants.ScheduleType.DEPARTURE)
    val dateType: LiveData<Constants.ScheduleType>
        get() = _dateType

    //тип аэропорта: прибытие/вылет
    private var _currentAirportType: Constants.ScheduleType = Constants.ScheduleType.DEPARTURE
    val currentAirportType: Constants.ScheduleType
        get() = _currentAirportType

    //обновление аэропорта вылета
    fun setDepartureAirport(airport: AirportUIModel) {
        _departure.value = airport
    }

    //обновление аэропорта прибытия
    fun setArrivalAirport(airport: AirportUIModel) {
        _arrival.value = airport
    }

    //обновление типа аэропорта (прибытие/вылет)
    fun setAirportType(type: Constants.ScheduleType) {
        _currentAirportType = type
    }

    //обновление даты
    fun setDate(date: Long?) {
        date?.let {
            _date.value = Date(date)
        }
    }

    //обновление типа даты: вылет/прибытие
    fun setDateType(type: Constants.ScheduleType) {
        _dateType.value = type
    }
}