package com.example.os_frontend.firestore.category

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val ProductImg: String = "",
    var storeName: String = ""
)
