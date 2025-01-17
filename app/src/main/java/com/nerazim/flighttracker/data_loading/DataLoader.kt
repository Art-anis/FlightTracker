package com.nerazim.flighttracker.data_loading

import android.content.Context
import android.content.SharedPreferences

//общий интерфейс загрузчика
interface DataLoader {

    //функция предварительного заполнения данных
    suspend fun prepopulate(
        pref: SharedPreferences,
        context: Context,
        stringResources: Map<String, String> //список строковых ресурсов для сохранения значений в SharedPref
    )
}