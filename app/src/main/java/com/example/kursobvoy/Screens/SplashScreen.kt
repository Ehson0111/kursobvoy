package com.example.kursobvoy.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
        if (!isLoading) {
            if (error == null) {
                onDataLoaded() // Переходим на каталог
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (error != null) {
                    // Показываем ошибку, если она есть
                    Text(text = error)
                } else {
                    // Показываем анимацию, пока данные загружаются
                    ComposeLottieAnimation(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                }
            }
        },
        backgroundColor = Color(0xFFFFFFFF)
    )
}

@Composable
fun ComposeLottieAnimation(modifier: Modifier) {
    val clipSpecs = LottieClipSpec.Progress(
        min = 0.0f,
        max = 0.44f
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))

    if (composition == null) {
        Log.e("SplashScreen", "Lottie composition failed to load")
        return
    }

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = Int.MAX_VALUE, // Зацикливаем анимацию, пока данные загружаются
        clipSpec = clipSpecs
    )
}