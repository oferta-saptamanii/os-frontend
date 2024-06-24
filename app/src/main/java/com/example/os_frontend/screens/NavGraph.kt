package com.example.os_frontend.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.os_frontend.NavigationBar
import com.google.relay.compose.ColumnScopeInstanceImpl.weight

@Composable
fun SetupNavGraph(navController: NavHostController) {
   Column {
       NavHost(
           navController = navController,
           startDestination = Screen.First.route,
           modifier = Modifier.weight(1f)
       ){
           composable(Screen.First.route){
               FirstScreen(navController)
           }
           composable(Screen.Second.route){
               SecondScreen()
           }
           composable(Screen.Third.route){
               ThirdScreen()
           }
           composable(Screen.Fourth.route){
               FourthScreen()
           }
       }
       NavigationBar(navController = navController)
   }
}