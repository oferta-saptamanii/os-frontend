package com.example.os_frontend.firestore


data class Product(
    val AvailableDate: String = "",
    val Category: String = "",
    val CurrentPrice: String = "",
    val DiscountPercentage: String? = "",
    val FullTitle: String = "",
    val Image: String = "",
    val OriginalPrice: String? = "",
    val ProductImg: String? = "",
    val ProductUrl: String = ""
)
