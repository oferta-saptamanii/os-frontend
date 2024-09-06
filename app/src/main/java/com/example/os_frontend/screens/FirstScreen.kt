package com.example.os_frontend.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.shop.ListContentProduct
import com.example.os_frontend.screens.shop.SearchViewBar
import com.example.os_frontend.screens.shop.location.HeaderLocation
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(navController: NavController, cartViewModel: CartViewModel) {
    var products by rememberSaveable { mutableStateOf(listOf<Product>()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repo = FirestoreRepository(context)


    LaunchedEffect(key1 = Unit){
        scope.launch{
            products = repo.fetchProducts("Lidl", "Oferte de Luni ")
        }

    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 8.dp, bottom = 8.dp))
    {
        HeaderLocation()
        SearchViewBar(hint = "Search for products")
        ListContentProduct(products, navController, cartViewModel)
    }
}

