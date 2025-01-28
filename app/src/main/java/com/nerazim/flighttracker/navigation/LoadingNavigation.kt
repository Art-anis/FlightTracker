package com.nerazim.flighttracker.navigation

import androidx.navigation.NavController

//навигация к экрану загрузки
fun NavController.onNavigateToLoading() {
    navigate(Routes.Loading.route)
}