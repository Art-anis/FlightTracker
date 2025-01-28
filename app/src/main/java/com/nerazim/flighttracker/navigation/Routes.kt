package com.nerazim.flighttracker.navigation

import com.nerazim.network.util.Constants

//класс маршрутов для навигационного графа
sealed class Routes(
    val route: String
) {
    data object FlightsSearch: Routes(
        route = "Поиск рейсов"
    )

    data class AirportSearch(
        val type: Constants.ScheduleType = Constants.ScheduleType.DEPARTURE
    ): Routes(
        route = "Поиск аэропорта"
    )

    data object FlightsList: Routes(
        route = "Результат поиска рейсов"
    )

    data object FlightDetails: Routes(
        route = "Подробности о рейсе"
    )

    data object Loading: Routes(
        route = "Загрузка"
    )
}