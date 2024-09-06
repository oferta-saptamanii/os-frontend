package com.example.os_frontend.firestore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE Category = :categoryName")
    suspend fun getProductsByCategory(categoryName: String): List<Product>

    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    suspend fun getProductsByIds(productIds: List<Int>): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
}

@Dao
interface CategoryDao {
    @Query("SELECT id FROM categories WHERE name = :categoryName")
    suspend fun getCategoryIdByName(categoryName: String): Int?

    @Query("SELECT * FROM categories WHERE storeName = :storeName")
    suspend fun getCategoriesByStore(storeName: String): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}


@Dao
interface StoreDao {
    @Query("SELECT id FROM stores WHERE name = :storeName LIMIT 1")
    suspend fun getStoreIdByName(storeName: String): Int?

    @Query("SELECT * FROM stores")
    suspend fun getAllStores(): List<Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStores(stores: List<Store>)

    @Query("DELETE FROM stores")
    suspend fun deleteAllStores()
}

@Dao
interface CategoryProductCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryProductCrossRefs(crossRefs: List<CategoryProductCrossRef>)

    @Query("SELECT productId FROM CategoryProductCrossRef WHERE categoryId = :categoryId")
    suspend fun getProductIdsForCategory(categoryId: Int): List<Int>
}

@Dao
interface StoreCategoryCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoreCategoryCrossRefs(crossRefs: List<StoreCategoryCrossRef>)

    @Query("SELECT categoryId FROM StoreCategoryCrossRef WHERE storeId = :storeId")
    suspend fun getCategoryIdsForStore(storeId: Int): List<Int>
}



