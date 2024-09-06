package com.example.os_frontend.firestore

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(
    entities = [
        Product::class,
        Category::class,
        Store::class,
        CategoryProductCrossRef::class,
        StoreCategoryCrossRef::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun storeDao(): StoreDao
    abstract fun categoryProductCrossRefDao(): CategoryProductCrossRefDao
    abstract fun storeCategoryCrossRefDao(): StoreCategoryCrossRefDao
}
