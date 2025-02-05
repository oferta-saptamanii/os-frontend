package com.example.os_frontend.firestore.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoreDao {
    @Query("SELECT * FROM stores")
    suspend fun getAllStores(): List<Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStores(stores: List<Store>)


}