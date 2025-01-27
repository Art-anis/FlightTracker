package com.nerazim.flighttracker.screens

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nerazim.flighttracker.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//экран начальной загрузки данных
@Composable
fun LoadingScreen(
    pref: SharedPreferences, //SharedPref
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    launchStateUpdate: () -> Unit //функция завершения загрузки
) {
    //прогресс загрузки аэропортов и городов
    var progress by rememberSaveable { mutableFloatStateOf(0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = stringResource(R.string.total_progress)
    ).value

    var buttonVisible by rememberSaveable { mutableStateOf(false) }

    //строковые ресурсы (объявляются здесь, так как нельзя объявить их в scope
    val airportProgressResource = stringResource(R.string.airports_loaded)
    val cityProgressResource = stringResource(R.string.cities_loaded)
    val airportListSizeResource = stringResource(R.string.airports_size)
    val cityListSizeResource = stringResource(R.string.cities_size)

    //scope для корутины
    val scope = rememberCoroutineScope()

    //запускаем корутину только один раз, игнорируя рекомпозиции
    LaunchedEffect(Unit) {
        scope.launch {
            while (progress != 1f) {
                delay(100)
                //получаем размеры списков и прогресс
                val airportListSize = pref.getInt(airportListSizeResource, 0)
                val citiesListSize = pref.getInt(cityListSizeResource, 0)
                val newAirportProgress = pref.getInt(airportProgressResource, 0)
                val newCityProgress = pref.getInt(cityProgressResource, 0)

                //вычисляем итоговый прогресс
                progress = (newCityProgress + newAirportProgress).toFloat() /
                        (citiesListSize + airportListSize)
            }
            buttonVisible = true
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //информативный текст для пользователя
        Text(
            text = stringResource(R.string.data_loading_notification),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(32.dp))
        //шкала прогресса вместе с текстовым представлением прогресса
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            //шкала прогресса
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .height(16.dp)
                    .weight(0.8f)
            )
            Spacer(Modifier.width(16.dp))
            //текстовое представление прогресса
            Text(
                text = "%.2f".format(progress * 100) + "%",
                modifier = Modifier.weight(0.2f)
            )
        }
        //кнопка "Продолжить"
        if (buttonVisible) {
            Spacer(Modifier.height(32.dp))
            Button(onClick = {
                //по нажатию завершаем начальную конфигурацию
                launchStateUpdate()
                onNavigateBack()
            }) {
                Text(
                    text = stringResource(R.string.proceed)
                )
            }
        }
    }
}