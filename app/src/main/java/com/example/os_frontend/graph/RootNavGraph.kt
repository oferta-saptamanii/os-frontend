package com.example.os_frontend.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.os_frontend.screens.FirstScreen
import com.example.os_frontend.screens.cart.CartViewModel

@Composable
fun RootNavigationGraph(navController: NavHostController, cartViewModel: CartViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
         //   SplashScreen(navController = navController)
        }

        composable(route = Screen.OnBoarding.route) {
           // OnBoardingScreen(navController = navController)
        }

//        composable(route = Graph.MAIN) {
//            FirstScreen(navController, cartViewModel)
//        }
    }
}