package com.example.os_frontend.firestore.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE storeName = :storeName")
    suspend fun getCategoriesByStore(storeName: String): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

}




