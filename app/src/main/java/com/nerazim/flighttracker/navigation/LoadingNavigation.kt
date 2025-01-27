package com.nerazim.flighttracker.navigation

import android.content.SharedPreferences
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nerazim.flighttracker.screens.LoadingScreen

//вершина навграфа - загрузка начальных данных
fun NavGraphBuilder.loadingDestination(
    pref: SharedPreferences,
    modifier: Modifier,
    launchStateUpdate: () -> Unit,
    onNavigateBack: () -> Unit
) {
    //экран загрузки
    composable(Routes.Loading.route) {
        LoadingScreen(
            pref = pref,
            modifier = modifier,
            launchStateUpdate = launchStateUpdate,
            onNavigateBack = onNavigateBack,
        )
    }
}

//навигация к экрану загрузки
fun NavController.onNavigateToLoading() {
    navigate(Routes.Loading.route)
}