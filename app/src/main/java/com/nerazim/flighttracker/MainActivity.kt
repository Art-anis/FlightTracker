package com.nerazim.flighttracker

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nerazim.flighttracker.navigation.Routes
import com.nerazim.flighttracker.navigation.onNavigateToAirports
import com.nerazim.flighttracker.navigation.onNavigateToFlightDetails
import com.nerazim.flighttracker.navigation.onNavigateToFlights
import com.nerazim.flighttracker.navigation.onNavigateToLoading
import com.nerazim.flighttracker.screens.AirportSearchScreen
import com.nerazim.flighttracker.screens.FlightDetailsScreen
import com.nerazim.flighttracker.screens.FlightSearchScreen
import com.nerazim.flighttracker.screens.FlightsListScreen
import com.nerazim.flighttracker.screens.LoadingScreen
import com.nerazim.flighttracker.ui.theme.FlightTrackerTheme
import com.nerazim.flighttracker.viewmodels.FlightSearchViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel

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
                        startDestination = Routes.FlightsSearch.route
                    ) {
                        //экран поиска рейсов
                        composable(route = Routes.FlightsSearch.route) {
                            //получаем инстанс общей viewmodel
                            val backStackEntry = remember {
                                navController.getBackStackEntry(route = Routes.FlightsSearch.route)
                            }
                            val sharedViewModel: FlightSearchViewModel = koinNavViewModel(
                                viewModelStoreOwner = backStackEntry
                            )
                            //сам экран
                            FlightSearchScreen(
                                pref = pref,
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToLoading = {
                                    navController.onNavigateToLoading()
                                },
                                onNavigateToFlights = {
                                    navController.onNavigateToFlights()
                                },
                                onNavigateToAirports = { type ->
                                    sharedViewModel.setAirportType(type)
                                    navController.onNavigateToAirports()
                                },
                                viewModel = sharedViewModel
                            )
                        }

                        //экран загрузки
                        composable(route = Routes.Loading.route) {
                            LoadingScreen(
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
                        }

                        //экран поиска аэропорта
                        composable(
                            route = Routes.AirportSearch().route
                        ) {
                            //получаем инстанс общей viewmodel
                            val backStackEntry = remember {
                                navController.getBackStackEntry(Routes.FlightsSearch.route)
                            }
                            val sharedViewModel: FlightSearchViewModel = koinNavViewModel(
                                viewModelStoreOwner = backStackEntry
                            )
                            AirportSearchScreen(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                viewModel = sharedViewModel
                            )
                        }

                        //экран просмотра списка рейсов
                        composable(route = Routes.FlightsList.route) {
                            FlightsListScreen(
                                modifier = Modifier.padding(innerPadding),
                                onNavigateToDetails = {
                                    navController.onNavigateToFlightDetails()
                                },
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        //экран просмотра деталей рейса
                        composable(route = Routes.FlightDetails.route) {
                            FlightDetailsScreen(
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
}