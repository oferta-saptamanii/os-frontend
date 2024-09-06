package com.example.os_frontend.firestore

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val AvailableDate: String? = "",
    var Category: String = "",
    val CurrentPrice: String? = "",
    val DiscountPercentage: String? = "",
    val FullTitle: String = "",
    val Image: String = "",
    val OriginalPrice: String? = "",
    val ProductImg: String? = "",
    val ProductUrl: String = "",
    val Subcategory: String? = "",
    val Quantity: String? = "",
    val StoreName: String = ""
)

@Entity(primaryKeys = ["categoryId", "productId"])
data class CategoryProductCrossRef(
    val categoryId: Int,
    val productId: Int
)
