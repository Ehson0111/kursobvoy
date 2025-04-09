package com.example.kursobvoy.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kursobvoy.R
@Composable
fun SplashScreen(
    navController: NavController,
    isLoading: Boolean,
    error: String?,
    onDataLoaded: () -> Unit
) {
    // Если загрузка завершена и нет ошибки, переходим на каталог
    LaunchedEffect(isLoading, error) {
        if (!isLoading && error == null) {
            onDataLoaded()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(0xFFFFFFFF)
    ) { innerPadding ->  // Получаем innerPadding от Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)  // Используем полученный padding
                .padding(16.dp),  // Добавляем дополнительный отступ
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (error != null) {
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                ComposeLottieAnimation(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp)  // Добавил явный размер для анимации
                )
            }
        }
    }
}

@Composable
fun ComposeLottieAnimation(modifier: Modifier) {
    val clipSpecs = LottieClipSpec.Progress(
        min = 0.0f,
        max = 0.44f
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))

    if (composition == null) {
        Text("Loading...")  // Fallback на случай ошибки загрузки
        return
    }

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = Int.MAX_VALUE,
        clipSpec = clipSpecs
    )
}