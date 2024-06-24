package com.example.os_frontend.firestore

data class Category(
    val name: String = "",
    val products: List<Product> = emptyList()
)
