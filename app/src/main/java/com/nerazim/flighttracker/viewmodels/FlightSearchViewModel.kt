package com.nerazim.flighttracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerazim.db.entities.AirportEntity
import com.nerazim.domain.usecases.GetAirportsUseCase
import com.nerazim.domain.usecases.GetCityUseCase
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.util.toAirportUIModel
import com.nerazim.network.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date

//viewmodel поиска рейсов
class FlightSearchViewModel(
    private val getAirportsUseCase: GetAirportsUseCase,
    private val getCityUseCase: GetCityUseCase
): ViewModel() {

    //пункт вылета
    private val _departure: MutableLiveData<AirportUIModel> = MutableLiveData()
    val departure: LiveData<AirportUIModel> = _departure

    //пункт прибытия
    private val _arrival: MutableLiveData<AirportUIModel> = MutableLiveData()
    val arrival: LiveData<AirportUIModel> = _arrival

    //дата вылета/прибытия
    private val _date: MutableLiveData<Date> = MutableLiveData()
    val date: LiveData<Date> = _date

    //список аэропортов
    private val _airports: MutableLiveData<List<AirportUIModel>> = MutableLiveData()
    val airports: LiveData<List<AirportUIModel>> = _airports

    //тип аэропорта: прибытие/вылет
    private var _currentAirportType: Constants.ScheduleType = Constants.ScheduleType.DEPARTURE
    val currentAirportType: Constants.ScheduleType
        get() = _currentAirportType

    //ссылка на корутину, которая грузит данные
    private var loadingJob: Job = Job()
    //флаг загрузки данных из БД
    private var isLoading: Boolean = false

    //получение аэропортов по подсказке
    fun getAirports(hint: String) {
        //если мы ранее отправляли запрос на загрузку, отменяем его
        if (isLoading) {
            loadingJob.cancel()
            isLoading = false
        }
        //запускаем запрос
        loadingJob = viewModelScope.launch {
            isLoading = true
            //получаем аэропорты
            getAirportsUseCase.execute(hint).flowOn(Dispatchers.IO).collect { result: List<AirportEntity> ->
                val list = result.map {
                    //на каждом шаге проверяем, что этот запрос не был отменен
                    ensureActive()
                    //получаем название города
                    val cityName = if (it.cityIata != "empty") {
                        getCityUseCase.execute(it.cityIata).name
                    } else {
                        ""
                    }
                    //преобразуем результат в модель для UI
                    it.toAirportUIModel(cityName)
                }
                //обновление списка
                _airports.value = list
            }
        }
    }

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
}