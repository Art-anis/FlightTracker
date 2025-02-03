package com.nerazim.flighttracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nerazim.db.entities.AirportEntity
import com.nerazim.domain.repositories.AirportsRepository
import com.nerazim.domain.repositories.CitiesRepository
import com.nerazim.flighttracker.ui_models.AirportUIModel
import com.nerazim.flighttracker.util.toAirportUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

//viewmodel поиска аэропортов
class AirportSearchViewModel(
    private val airportsRepository: AirportsRepository,
    private val citiesRepository: CitiesRepository
): ViewModel() {

    //список аэропортов
    private val _airports: MutableLiveData<List<AirportUIModel>> = MutableLiveData()
    val airports: LiveData<List<AirportUIModel>>
        get() = _airports

    //история поиска аэропортов
    private val _history: MutableLiveData<List<AirportUIModel>> = MutableLiveData()
    val history: LiveData<List<AirportUIModel>>
        get() = _history

    //ссылка на корутину, которая грузит данные
    private var loadingJob: Job = Job()
    //флаг загрузки данных из БД
    private var isLoading: Boolean = false

    //получение истории поиска
    fun getHistory() {
        viewModelScope.launch {
            //получаем историю
            _history.value = airportsRepository
                .getHistory()
                //отфильтровываем те, которые никогда не искали (история поиска меньше, чем 3)
                .filter { it.lastSearchTime != 0L }
                //преобразование оставшихся элементов в UI-модели
                .map {
                    val cityName = if (it.cityIata != "empty") {
                        citiesRepository.getCityByIata(it.cityIata).name
                    } else {
                        ""
                    }
                    //преобразуем результат в модель для UI
                    it.toAirportUIModel(cityName)
                }
        }
    }

    //выбор аэропорта
    fun selectAirport(name: String, time: Long) {
        viewModelScope.launch {
            //отмечаем выбор аэропорта обновление времени выбора
            airportsRepository.selectAirport(name, time)
        }
    }

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
            airportsRepository
                .getAirportsFromDb(hint)
                .flowOn(Dispatchers.IO)
                .collect { result: List<AirportEntity> ->
                val list = result.map {
                    //на каждом шаге проверяем, что этот запрос не был отменен
                    ensureActive()
                    //получаем название города
                    val cityName = if (it.cityIata != "empty") {
                        citiesRepository.getCityByIata(it.cityIata).name
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
}