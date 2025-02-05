package com.nerazim.flighttracker.app

import android.app.Application
import android.preference.PreferenceManager
import com.nerazim.db.di.databaseModule
import com.nerazim.domain.di.domainModule
import com.nerazim.flighttracker.R
import com.nerazim.domain.data_loading.AirportLoader
import com.nerazim.domain.data_loading.CityLoader
import com.nerazim.flighttracker.di.appModule
import com.nerazim.network.di.networkModule
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        //запуск DI
        startKoin {
            androidLogger(Level.DEBUG)

            androidContext(applicationContext)

            modules(listOf(appModule, domainModule, networkModule, databaseModule))
        }

        //SharedPref
        val pref = PreferenceManager.getDefaultSharedPreferences(baseContext)
        //если БД пустая или загрузка была прервана, запустить заново
        if (!pref.contains(getString(R.string.first_launch)) || pref.getBoolean(getString(R.string.first_launch), false)) {
            //кладем true в SharedPref
            val editor = pref.edit()
            editor
                .putBoolean(getString(R.string.first_launch), true)
                .apply()
            editor.clear()

            //запускаем корутину
            MainScope().launch {
                //в отдельной корутине проверяем базу городов
                launch {
                    //inject загрузчика
                    val loader by inject<CityLoader>()
                    loader.prepopulate(
                        pref = pref,
                        context = applicationContext,
                        stringResources = mapOf(
                            "loaded" to getString(R.string.cities_loaded),
                            "size" to getString(R.string.cities_size)
                        )
                    )
                }
                //аналогично делаем с базой аэропортов
                launch {
                    val loader by inject<AirportLoader>()
                    loader.prepopulate(
                        pref = pref,
                        context = applicationContext,
                        stringResources = mapOf(
                            "loaded" to getString(R.string.airports_loaded),
                            "size" to getString(R.string.airports_size)
                        )
                    )
                }
            }
        }
    }

}