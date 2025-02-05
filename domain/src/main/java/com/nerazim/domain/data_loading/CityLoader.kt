package com.nerazim.domain.data_loading

import android.content.Context
import android.content.SharedPreferences
import com.nerazim.db.dao.CityDAO
import com.nerazim.db.entities.CityEntity
import org.koin.java.KoinJavaComponent.inject

//загрузчик данных о городах
class CityLoader: DataLoader {

    //предварительное заполнение БД
    override suspend fun prepopulate(
        pref: SharedPreferences,
        context: Context,
        stringResources: Map<String, String>
    ) {
        //inject dao
        val dao = inject<CityDAO>(CityDAO::class.java).value
        context.assets.open("Cities.csv").bufferedReader().use {
            //считываем заголовок
            it.readLine()
            var loaded = 0

            //переменные для работы с SharedPref
            val editor = pref.edit() //редактор
            val loadedResource = stringResources["loaded"] //ключ для количества загруженных элементов
            val sizeResource = stringResources["size"] //ключ для значения размера списка

            //перебор всех строк файла, кроме строки заголовков
            val actualCities = it.lineSequence()
                //разбиение строки на фактические значения
                .map { row -> row.split(';') }
                //фильтр конкретных значений, которые участвуют в формировании сущности
                .map { row -> mapOf("iata" to row[1], "name" to row[3], "timezone" to row[6]) }
                .toList()

            //добавление в SharedPref размера списка городов
            editor
                .putInt(sizeResource, actualCities.size)
                .apply()

            //создание сущностей и добавление их в БД
            actualCities
                .forEach { row ->
                    val city = CityEntity(
                        iata = row["iata"]!!,
                        name = row["name"]!!,
                        timeZone = row["timezone"]!!
                    )
                    //вставка города в БД
                    dao.addCity(city)

                    //обновляем количество загруженных городов и добавляем его в SharedPref
                    loaded++
                    editor
                        .putInt(loadedResource, loaded)
                        .commit()
                }
        }
    }
}