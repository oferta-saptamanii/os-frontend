package com.example.os_frontend.screens.shop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.category.Category
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.explore.products.ProductViewModel
import com.example.os_frontend.screens.shop.location.HeaderLocation
import kotlinx.coroutines.delay

@Composable
fun ShopScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel,
) {
    var productDiscounted by rememberSaveable { mutableStateOf(listOf<Product>()) }
    var productsSlideShow by rememberSaveable { mutableStateOf(listOf<Product>()) }
    var productLimited by rememberSaveable { mutableStateOf(listOf<Product>()) }
    var categories by rememberSaveable {
        mutableStateOf(listOf<Category>())
    }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val repo = remember { FirestoreRepository(context) }


    LaunchedEffect(Unit) {
        isLoading = true
        categories = repo.fetchCategories("Aggregated")
        val rawProducts = repo.fetchDiscountedProducts()
        val regex = Regex("^-?[0-9]{1,2}%?$")

        val filteredProducts = rawProducts.filter { product ->
            product.DiscountPercentage?.trim()?.matches(regex) == true
        }

        val sortedProducts = filteredProducts
            .sortedByDescending { product ->
                var discount = product.DiscountPercentage?.trim() ?: ""

                if (!discount.startsWith("-")) {
                    discount = "-$discount"
                }

                if (!discount.endsWith("%")) {
                    discount += "%"
                }

                val numericDiscount = discount.removePrefix("-").removeSuffix("%").toIntOrNull() ?: 0
                numericDiscount
            }
        productDiscounted = sortedProducts.take(250)

        productsSlideShow = repo.fetchRandomProducts()
        productLimited = repo.fetchLimitedStock().shuffled()
        delay(1000)
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, bottom = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderLocation()
            SlideShow(productsSlideShow)
            Categories(categories, navController)
            Discount(productDiscounted, cartViewModel)
            LimitedStock(productLimited, cartViewModel)
        }
    }
}

