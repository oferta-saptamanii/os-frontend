package com.example.os_frontend.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ThirdScreen(cartViewModel: CartViewModel ) {
    val cartItems by  cartViewModel.cartItems.collectAsState()
    Column(modifier = Modifier.background(Color.White)) {
        ListContentCart(
            cartItems = cartItems,
            cartViewModel = cartViewModel,
        )
    }
}

