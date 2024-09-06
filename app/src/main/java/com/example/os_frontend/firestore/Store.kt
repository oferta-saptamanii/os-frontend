package com.example.os_frontend.firestore

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stores")
data class Store(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
)

@Entity(primaryKeys = ["storeId", "categoryId"])
data class StoreCategoryCrossRef(
    val storeId: Int,
    val categoryId: Int
)
