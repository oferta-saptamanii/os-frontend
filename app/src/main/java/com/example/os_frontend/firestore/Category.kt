package com.example.os_frontend.firestore

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val ProductImg: String = "",
    val storeName: String = ""
)
