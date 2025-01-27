package com.nerazim.flighttracker

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nerazim.flighttracker.navigation.Routes
import com.nerazim.flighttracker.navigation.airportSearchDestination
import com.nerazim.flighttracker.navigation.flightDetailsDestination
import com.nerazim.flighttracker.navigation.flightSearchDestination
import com.nerazim.flighttracker.navigation.flightsListDestination
import com.nerazim.flighttracker.navigation.loadingDestination
import com.nerazim.flighttracker.navigation.onNavigateToAirports
import com.nerazim.flighttracker.navigation.onNavigateToFlightDetails
import com.nerazim.flighttracker.navigation.onNavigateToFlights
import com.nerazim.flighttracker.navigation.onNavigateToLoading
import com.nerazim.flighttracker.ui.theme.FlightTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //настраиваем SharedPref для работы с флагами пустых БД
        val pref = PreferenceManager.getDefaultSharedPreferences(baseContext)

        setContent {
            FlightTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //это single-activity приложение, поэтому нужен navController
                    val navController = rememberNavController()

                    //создаем хост с текущим контроллером и стартовой вершиной в графе
                    NavHost(
                        navController = navController,
                        startDestination = Routes.FlightsSearch.route) {
                        //экран поиска рейсов
                        flightSearchDestination(
                            pref = pref,
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToLoading = {
                                navController.onNavigateToLoading()
                            },
                            onNavigateToFlights = {
                                navController.onNavigateToFlights()
                            },
                            onNavigateToAirports = {
                                navController.onNavigateToAirports()
                            }
                        )

                        //экран загрузки
                        loadingDestination(
                            pref = pref,
                            modifier = Modifier.padding(innerPadding),
                            launchStateUpdate = {
                                //обновление флага первого запуска в SharedPref
                                val editor = pref.edit()
                                editor
                                    .putBoolean(getString(R.string.first_launch), false)
                                    .apply()
                                editor.clear()
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )

                        //экран поиска аэропорта
                        airportSearchDestination(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )

                        //экран просмотра списка рейсов
                        flightsListDestination(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToDetails = {
                                navController.onNavigateToFlightDetails()
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )

                        //экран просмотра деталей рейса
                        flightDetailsDestination(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}