package com.example.os_frontend.screens

sealed class Screen(val route: String) {
    data object First : Screen(route = "firstScreen")
    data object Second : Screen(route = "secondScreen")
    data object Third : Screen(route = "thirdScreen")
    data object Fourth : Screen(route = "fourthScreen")
}