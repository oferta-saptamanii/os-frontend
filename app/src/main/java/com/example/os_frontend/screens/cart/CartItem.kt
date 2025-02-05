package com.example.os_frontend.screens.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val name: String,
    val image: String?,
    val price: String,
    val quantity: Int,
    val storeName: String,
)
