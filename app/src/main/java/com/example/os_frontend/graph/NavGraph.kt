package com.example.os_frontend.graph

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.os_frontend.NavigationBar
import com.example.os_frontend.firestore.DatabaseProvider
import com.example.os_frontend.screens.cart.CartScreen
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.catalogs.CatalogPDF
import com.example.os_frontend.screens.catalogs.CatalogsScreen
import com.example.os_frontend.screens.catalogs.StoresScreen
import com.example.os_frontend.screens.catalogs.onboarding.OnBoardingScreen
import com.example.os_frontend.screens.explore.ProductsScreen
import com.example.os_frontend.screens.explore.StoreScreen
import com.example.os_frontend.screens.explore.products.AuchanScreen
import com.example.os_frontend.screens.explore.products.KauflandScreen
import com.example.os_frontend.screens.explore.products.LidlScreen
import com.example.os_frontend.screens.explore.products.MegaImageScreen
import com.example.os_frontend.screens.explore.products.PennyScreen
import com.example.os_frontend.screens.explore.products.ProductViewModel
import com.example.os_frontend.screens.explore.products.ProductsScreenforKaufland
import com.example.os_frontend.screens.explore.products.ProfiScreen
import com.example.os_frontend.screens.search.SearchScreen
import com.example.os_frontend.screens.shop.ShopScreen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavGraph(navController: NavHostController, context: Context) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val cartDao = DatabaseProvider.provideCartDao(context)
    val cartViewModel = CartViewModel(cartDao)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = currentBackStackEntry?.destination?.route
            val excludedRoutes = listOf(
                BottomNavigationItemScreen.First.route,
                BottomNavigationItemScreen.Second.route,
                BottomNavigationItemScreen.Third.route,
                BottomNavigationItemScreen.Fourth.route,
                BottomNavigationItemScreen.Fifth.route,
                "onboarding_screen/{storeName}"
            )

            if (currentRoute !in excludedRoutes) {
                val title = when {
                    currentRoute?.startsWith("products") == true -> {
                        currentBackStackEntry?.arguments?.getString("categoryName") ?: "Products"
                    }
                    currentRoute?.startsWith("categories") == true -> {
                        currentBackStackEntry?.arguments?.getString("OffersDev") ?: "Categories"
                    }
                    else -> ""
                }


                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                            },
                    navigationIcon = {
                        if (navController.previousBackStackEntry != null) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        bottomBar = {
            NavigationBar(navController = navController, cartViewModel = cartViewModel)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Graph.MAIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            mainNavGraph(navController, context, cartViewModel)
        }
    }
}


fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    context: Context,
    cartViewModel: CartViewModel
) {
    val productDao = DatabaseProvider.provideProductDao(context)
    val productViewModel = ProductViewModel(productDao)

    navigation(
        startDestination = BottomNavigationItemScreen.First.route,
        route = Graph.MAIN
    ) {



        // Other screens
        composable(BottomNavigationItemScreen.First.route) {
            ShopScreen(navController, cartViewModel, productViewModel)
        }
        composable(BottomNavigationItemScreen.Second.route){
            SearchScreen(productViewModel = productViewModel, cartViewModel = cartViewModel)
        }
        composable(BottomNavigationItemScreen.Third.route) {
            StoreScreen(navController, productViewModel)
        }

        // Explore Section
        composable(Screen.Lidl.route) {
            LidlScreen(navController)
        }
        composable(Screen.Kaufland.route) {
            KauflandScreen(navController)
        }
        composable(Screen.Auchan.route) {
            AuchanScreen(navController)
        }
        composable(Screen.Penny.route) {
            PennyScreen(navController)
        }
        composable(Screen.Profi.route) {
            ProfiScreen(navController)
        }
        composable(Screen.MegaImage.route) {
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
                        cartViewModel = cartViewModel,
                        productViewModel = productViewModel
                    )
                }
                else -> {
                    ProductsScreen(
                        storeName = storeName,
                        navController = navController,
                        categoryName = categoryName,
                        cartViewModel = cartViewModel,
                        productViewModel = productViewModel
                    )
                }
            }
        }

        composable(
            route = "products/{categoryName}",
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""

            ProductsScreen(
                storeName = "Aggregated",
                navController = navController,
                categoryName = categoryName,
                cartViewModel = cartViewModel,
                productViewModel = productViewModel
            )
        }


        composable(BottomNavigationItemScreen.Fourth.route) {
            CartScreen(cartViewModel = cartViewModel)
        }
        composable(BottomNavigationItemScreen.Fifth.route){
            StoresScreen(navController)
        }
        composable(
            route = "onboarding_screen/{storeName}",
            arguments = listOf(navArgument("storeName") { type = NavType.StringType })
        ) { backStackEntry ->
            val storeName = backStackEntry.arguments?.getString("storeName") ?: ""
            OnBoardingScreen(storeName, navController)
        }

        composable(
            route = "catalogs/{storeName}/{city}/{store}",
            arguments = listOf(navArgument("storeName") { type = NavType.StringType })
        ) { backStackEntry ->
            val storeName = backStackEntry.arguments?.getString("storeName") ?: ""
            val cityName = backStackEntry.arguments?.getString("city") ?: ""
            val store = backStackEntry.arguments?.getString("store") ?: ""

            CatalogsScreen(
                storeName = storeName,
                city = cityName,
                store = store,
                navController = navController
            )
        }
        composable(
            route = "catalog_pdf/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")
            CatalogPDF(url = url)
        }

    }
}



object Graph {
    const val SPLASH = "splash_graph"
    const val MAIN = "main_graph"
}