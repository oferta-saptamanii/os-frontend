package com.example.os_frontend.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.os_frontend.firestore.Product

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CartItem(val product: Product, val quantity: Int)

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val updatedCart = _cartItems.value.toMutableList()
            val existingCartItem = updatedCart.find { it.product == product }
            if(existingCartItem != null){
                updatedCart[updatedCart.indexOf(existingCartItem)] =
                    existingCartItem.copy(quantity = existingCartItem.quantity + 1)
            } else {
                updatedCart.add(CartItem(product, 1))
            }
            _cartItems.value = updatedCart
        }
    }

    fun removeFromCartByOne(product: Product) {
        viewModelScope.launch {
            val updatedCart = _cartItems.value.toMutableList()
            val existingCartItem = updatedCart.find { it.product == product }
            if (existingCartItem != null){
                if(existingCartItem.quantity > 1){
                    updatedCart[updatedCart.indexOf(existingCartItem)] =
                        existingCartItem.copy(quantity = existingCartItem.quantity - 1)
                } else {
                    updatedCart.remove(existingCartItem)
                }
            }
            _cartItems.value = updatedCart
        }
    }

    fun removeFromCart(product: Product){
        viewModelScope.launch{
            val updatedCart = _cartItems.value.toMutableList()
            val existingCartItem = updatedCart.find { it.product == product }
            if(existingCartItem != null){
                updatedCart.remove(existingCartItem)
            }
            _cartItems.value = updatedCart
        }
    }

    fun calculateTotalPrice(): Float {
        var totalPrice = 0f
        _cartItems.value.forEach { cartItem ->
            val price = cartItem.product.CurrentPrice?.toFloatOrNull() ?: 0f
            val quantity = cartItem.quantity.toFloat()
            totalPrice += price * quantity
        }
        return totalPrice
    }

    fun removeAllCartItems() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
        }
    }
}