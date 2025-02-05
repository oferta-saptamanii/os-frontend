package com.example.os_frontend.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.os_frontend.firestore.product.Product
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CartViewModel(private val cartDao: CartDao) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {

        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = cartDao.getAllCartItems()
            _cartItems.value = items
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val existingCartItem = _cartItems.value.find { it.productId == product.id }
            if(existingCartItem != null){
                val updatedItem = existingCartItem.copy(
                    quantity = existingCartItem.quantity + 1
                )
                cartDao.updateCartItem(updatedItem)
            } else {
                val newItem = CartItem(
                    productId = product.id,
                    name = product.FullTitle,
                    image = product.Image,
                    price = (product.CurrentPrice ?: 0).toString(),
                    quantity = 1,
                    storeName = product.StoreName?: ""
                )
                cartDao.insertCartItem(newItem)
            }
            loadCartItems()
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartDao.deleteCartItem(cartItem)
            loadCartItems()
        }
    }


    fun removeAllCartItems() {
        viewModelScope.launch {
            cartDao.clearCart()
            loadCartItems()
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity > 1) {
                val updatedItem = cartItem.copy(quantity = cartItem.quantity - 1)
                cartDao.updateCartItem(updatedItem)
                loadCartItems()
            } else {
                removeFromCart(cartItem)
            }
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            val updatedItem = cartItem.copy(quantity = cartItem.quantity + 1)
            cartDao.updateCartItem(updatedItem)
            loadCartItems()
        }
    }
}