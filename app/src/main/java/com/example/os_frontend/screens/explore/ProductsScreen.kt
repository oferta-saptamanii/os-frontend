package com.example.os_frontend.screens.explore

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.components.BottomSheet
import com.example.os_frontend.screens.explore.products.FilterSection
import com.example.os_frontend.screens.explore.products.ProductList
import com.example.os_frontend.screens.explore.products.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    navController: NavController,
    storeName: String,
    categoryName: String,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel
){
    val scope = rememberCoroutineScope()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val snackBarHostState = remember { SnackbarHostState() }

    var selectedFilter by remember { mutableStateOf("") }

    val isLoading by productViewModel.isLoading.collectAsState()
    val products by productViewModel.products.collectAsState()


    LaunchedEffect(key1 = storeName, key2 = categoryName, key3 = selectedFilter) {
        productViewModel.fetchFilteredProducts(storeName, categoryName, selectedFilter)
    }

    Log.d("ProductsScreen", "Products from viewmodel: $products")

    Scaffold(
        snackbarHost = { SnackbarHost(
            snackBarHostState,
            snackbar = { data->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                )
            }
        )
        }
    ) {

        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Se incarca produsele...")
            }
        } else{
            Column(modifier = Modifier.fillMaxSize()) {

                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { filter->
                        selectedFilter = filter
                        productViewModel.fetchFilteredProducts(storeName, categoryName, filter)
                    }
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(products) { product ->
                        ProductList(
                            product = product,
                            cartViewModel = cartViewModel,
                            onProductClick = {
                                selectedProduct = product
                                scope.launch(Dispatchers.IO) {
                                    sheetState.show()
                                }
                            },
                            onProductBuy = {
                                scope.launch(Dispatchers.IO) {
                                    snackBarHostState.showSnackbar(
                                        "Produs adaugat in cos",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }

                if (selectedProduct != null) {
                    BottomSheet(
                        product = selectedProduct!!,
                        //  sheetState = sheetState,
                        onClose = {
                            selectedProduct = null
                            scope.launch(Dispatchers.IO) {
                                sheetState.hide()
                            }
                        },
                        onProductBuy = {
                            cartViewModel.addToCart(selectedProduct!!)
                            scope.launch(Dispatchers.IO) {
                                sheetState.hide()
                                snackBarHostState.showSnackbar(
                                    "Produs adaugat in cos",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                }
            }
        }


    }
}

