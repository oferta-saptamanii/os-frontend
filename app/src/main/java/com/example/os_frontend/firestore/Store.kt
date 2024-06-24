package com.example.os_frontend.firestore


data class Store(
    val name: String = "",
    val categories: List<Category> = emptyList()
)
