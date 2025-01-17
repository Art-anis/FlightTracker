package com.nerazim.flighttracker.data_loading

import android.content.Context
import android.content.SharedPreferences
import com.nerazim.db.dao.AirportDAO
import com.nerazim.flighttracker.util.toAirportEntity
import com.nerazim.network.api.AirportsAPI
import com.nerazim.network.models.ResponseAirport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

//загрузчик данных аэропорта
class AirportLoader: DataLoader {

    //предварительное заполнение таблицы
    override suspend fun prepopulate(
        pref: SharedPreferences,
        context: Context,
        stringResources: Map<String, String>
    ) {
        //inject dao и api
        val api by inject<AirportsAPI>(AirportsAPI::class.java)
        val dao by inject<AirportDAO>(AirportDAO::class.java)
        //делаем запрос в api за всеми аэропортами
        val airports = withContext(Dispatchers.IO) {
            async {
                api.getAllAirports()
            }
        }
        //открываем файл с названиями российских аэропортов для изменения их названий
        context.assets.open("RussianAirports.csv").bufferedReader().use {
            //считываем заголовок
            it.readLine()

            //переменные для работы с SharedPref
            val editor = pref.edit() //редактор
            val loadedResource = stringResources["loaded"] //ключ для количества загруженных элементов
            val sizeResource = stringResources["size"] //ключ для значения размера списка
            //преобразовываем строки csv-файла в список
            val russianAirports = it.lineSequence()
                .map { row -> row.split(';') }
                .map { row -> mapOf("iata" to row[0], "name" to row[1]) }
                .toList()

            var loaded = 0 //количество загруженных аэропортов
            //работаем с полученными аэропортами
            val actualAirports = airports
                .await()
                //отсеиваем неизвестные
                .filterNot { airport -> airport.geonameId == "0" && airport.countryIso == "RU" }

            //добавляем в SharedPref размер списка городов
            editor
                .putInt(sizeResource, actualAirports.size)
                .apply()

            actualAirports
                //проходим по всем оставшимся
                .forEach { airport ->
                    //если это российский аэропорт, то добавляем его в базу после изменения названия
                    if (airport.countryIso == "RU") {
                        dao.addAirport(
                            remapAirportName(airport, russianAirports)
                                .toAirportEntity()
                        )
                    }
                    //иначе просто добавляем его в базу
                    else {
                        dao.addAirport(airport.toAirportEntity())
                    }
                    //обновляем количество загруженных аэропортов и добавляем его в SharedPref
                    loaded++
                    editor
                        .putInt(loadedResource, loaded)
                        .commit()
                }
        }
    }

    //функция изменения названия аэропорта
    private fun remapAirportName(airport: ResponseAirport, names: List<Map<String, String>>): ResponseAirport {
        //ищем аэропорт в списке с идентичным iata и возвращаем скопированную сущность с новым названием
        return airport.copy(name = names.filter { it["iata"] == airport.iata }[0]["name"]!!)
    }
}