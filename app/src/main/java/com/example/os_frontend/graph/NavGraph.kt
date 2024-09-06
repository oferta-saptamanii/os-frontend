package com.example.os_frontend.graph

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.os_frontend.AnimNavigationBar
import com.example.os_frontend.NavigationBar
import com.example.os_frontend.account.Account
import com.example.os_frontend.screens.FirstScreen
import com.example.os_frontend.screens.FourthScreen
import com.example.os_frontend.screens.cart.CartItem
import com.example.os_frontend.screens.explore.SecondScreen
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.cart.ThirdScreen
import com.example.os_frontend.screens.explore.MyTopAppBar
import com.example.os_frontend.screens.explore.stores.AuchanScreen
import com.example.os_frontend.screens.explore.stores.KauflandScreen
import com.example.os_frontend.screens.explore.stores.LidlScreen
import com.example.os_frontend.screens.explore.stores.MegaImageScreen
import com.example.os_frontend.screens.explore.stores.PennyScreen
import com.example.os_frontend.screens.explore.stores.ProductsScreen
import com.example.os_frontend.screens.explore.stores.ProductsScreenforKaufland
import com.example.os_frontend.screens.explore.stores.ProfiScreen

import com.example.os_frontend.screens.explore.stores.StoreScreen
import com.google.relay.compose.ColumnScopeInstanceImpl.weight


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val cartViewModel: CartViewModel = viewModel()
    Scaffold(
        topBar = {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val showTopAppBar = currentBackStackEntry?.destination?.route != BottomNavigationItemScreen.First.route &&
                    currentBackStackEntry?.destination?.route != BottomNavigationItemScreen.Second.route &&
                    currentBackStackEntry?.destination?.route != BottomNavigationItemScreen.Third.route &&
                    currentBackStackEntry?.destination?.route != BottomNavigationItemScreen.Fourth.route

            if (showTopAppBar) {
                MyTopAppBar(navController = navController)
            }
        },
        bottomBar = {
            AnimNavigationBar(navController = navController)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationItemScreen.First.route,
            modifier = Modifier.padding(bottom = 56.dp)
        ) {
            composable(BottomNavigationItemScreen.First.route) {
                FirstScreen(navController, cartViewModel)
            }
            composable(BottomNavigationItemScreen.Second.route) {
                SecondScreen(navController)
            }




            // explore section

            composable(Screen.Lidl.route) {
                LidlScreen(navController)
            }
            composable(Screen.Kaufland.route){
                KauflandScreen(navController)
            }
            composable(Screen.Auchan.route){
                AuchanScreen(navController)
            }
            composable(Screen.Penny.route){
                PennyScreen(navController)
            }
            composable(Screen.Profi.route){
                ProfiScreen(navController)
            }
            composable(Screen.MegaImage.route){
                MegaImageScreen(navController)
            }
            composable(
                route = "products/{storeName}/{categoryName}",
                arguments = listOf(
                    navArgument("storeName") { type = NavType.StringType },
                    navArgument("categoryName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val storeName = backStackEntry.arguments?.getString("storeName") ?: ""
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""

                when (storeName) {
                    "Kaufland" -> {
                        ProductsScreenforKaufland(
                            navController = navController,
                            categoryName = categoryName,
                            cartViewModel = cartViewModel
                        )
                    }
                    else -> {
                        ProductsScreen(
                            storeName = storeName,
                            navController = navController,
                            categoryName = categoryName,
                            cartViewModel = cartViewModel
                        )
                    }
                }
            }












            composable(BottomNavigationItemScreen.Third.route) {
                ThirdScreen(cartViewModel = cartViewModel)
            }
            composable(BottomNavigationItemScreen.Fourth.route) {
                FourthScreen()
            }
            composable(BottomNavigationItemScreen.Fifth.route){
                Account()
            }
        }
    }
}


//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun SetupNavGraph(navController: NavHostController) {
//    val cartViewModel: CartViewModel = viewModel()
//   Column{
//       NavHost(
//           navController = navController,
//        //   route = Graph.MAIN,
//           startDestination = BottomNavigationItemScreen.First.route,
//           modifier = Modifier.weight(1f)
//       ){
//           composable(BottomNavigationItemScreen.First.route){
//               FirstScreen(navController, cartViewModel)
//           }
//           composable(BottomNavigationItemScreen.Second.route){
//               SecondScreen(navController)
//           }
//
//
//           // lidl
////           composable(Screen.Lidl.route){
////               Lidl(navController)
////           }
////           composable(
////               route = "products/{categoryName}",
////               arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
////           ) { backStackEntry ->
////               val categoryName = backStackEntry.arguments?.getString("categoryName")
////               ProductsScreen(navController = navController, categoryName = categoryName ?: "", cartViewModel)
////           }
//
//           composable(Screen.Lidl.route) {
//               Scaffold(
//                   topBar = { MyTopAppBar(navController = navController) },
//                   modifier = Modifier.weight(1f)
//               ) {
//                   Lidl(navController = navController)
//               }
//           }
//           composable(
//               route = "products/{categoryName}",
//               arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
//           ) { backStackEntry ->
//               val categoryName = backStackEntry.arguments?.getString("categoryName")
//               Scaffold(
//                   topBar = { MyTopAppBar(navController = navController) },
//                   modifier = Modifier.weight(1f),
//               ) {
//                   ProductsScreen(navController = navController, categoryName = categoryName ?: ""
//                       , cartViewModel)
//               }
//           }
//
//
//
//           composable(BottomNavigationItemScreen.Third.route){
//               ThirdScreen(cartViewModel = cartViewModel)
//           }
//           composable(BottomNavigationItemScreen.Fourth.route){
//               FourthScreen()
//           }
//       }
//       NavigationBar(navController = navController, cartViewModel)
//   }
//}

/*
fun NavGraphBuilder.detailsNavGraph() {
    navigation(
        route = Graph.DETAILS,
        startDestination = Screen.Details.route
    ) {
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(PRODUCT_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen()
        }
    }
} */

/*
fun NavGraphBuilder.searchNavGraph() {
    navigation(
        route = Graph.SEARCH,
        startDestination = Screen.Search.route
    ) {
        composable(route = Screen.Search.route) {
            SearchScreen()
        }
    }
}*/


