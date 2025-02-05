package com.example.os_frontend.screens.explore.products

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.screens.explore.CategoryScreen

@Composable
fun LidlScreen(navController: NavHostController) {
    val context = LocalContext.current
    CategoryScreen(
        storeName = "Lidl",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName) }
    )
}

@Composable
fun PennyScreen(navController: NavHostController) {
    val context = LocalContext.current
    CategoryScreen(
        storeName = "Penny",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName) }
    )
}

@Composable
fun MegaImageScreen(navController: NavHostController) {
    val context = LocalContext.current
    CategoryScreen(
        storeName = "MegaImage",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName) }
    )
}

@Composable
fun AuchanScreen(navController: NavHostController) {
    val context = LocalContext.current
    CategoryScreen(
        storeName = "Auchan",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName) }
    )
}

@Composable
fun ProfiScreen(navController: NavHostController) {
    val context = LocalContext.current
    CategoryScreen(
        storeName = "Profi",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName) }
    )
}

@Composable
fun KauflandScreen(navController: NavHostController){
    val context = LocalContext.current
    Kaufland(
        storeName = "Kaufland",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName)}
    )
}

@Composable
fun AggregatedScreen(navController: NavHostController){
    val context = LocalContext.current
    CategoryScreen(
        storeName = "Aggregated",
        navController = navController,
        fetchCategories = { storeName -> FirestoreRepository(context).fetchCategories(storeName)}
    )
}