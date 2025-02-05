package com.example.os_frontend.screens.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.components.BottomSheet
import com.example.os_frontend.screens.components.ProductCard
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimitedStock(
    products: List<Product>,
    cartViewModel: CartViewModel
){
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Stoc limitat",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Black,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products){ product ->
                ProductCard(
                    product,
                    cartViewModel,
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
    }
    if (selectedProduct != null) {
        BottomSheet(
            product = selectedProduct!!,
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
                }
            }
        )
    }
}
