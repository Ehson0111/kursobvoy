package com.example.kursobvoy.Screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.kursobvoy.R

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val typography = Typography(defaultFontFamily = FontFamily(Font(R.font.roboto))
    )

    MaterialTheme(
        typography = typography,
        content = content
    )
}