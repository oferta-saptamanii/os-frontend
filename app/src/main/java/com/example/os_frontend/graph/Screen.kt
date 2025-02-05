package com.example.os_frontend.graph

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object OnBoarding : Screen("on_boarding_screen")
    data object Lidl : Screen("Lidl")
    data object Kaufland : Screen ("Kaufland")
    data object Penny : Screen ("Penny")
    data object MegaImage : Screen ("MegaImage")
    data object Auchan : Screen ("Auchan")
    data object Profi : Screen ("Profi")
    data object Aggregated : Screen ("Aggregated")
}

sealed class BottomNavigationItemScreen(val route: String){
    data object First : BottomNavigationItemScreen(route = "firstscreen")
    data object Second : BottomNavigationItemScreen(route = "secondscreen")
    data object Third : BottomNavigationItemScreen(route = "thirdscreen")
    data object Fourth : BottomNavigationItemScreen(route = "fourthscreen")
    data object Fifth : BottomNavigationItemScreen(route = "fifthscreen")
}