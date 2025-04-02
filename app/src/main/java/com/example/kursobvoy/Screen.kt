package com.example.kursobvoy


sealed class Screen(val route: String) {
//    object sayt : Screen("sayt")
    object Splash : Screen("Splash")
    object catalogue : Screen("catalogue")
}