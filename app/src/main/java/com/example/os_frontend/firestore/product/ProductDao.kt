package com.example.os_frontend.firestore.product

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

    // functie special pt a detecta aggregated la afisarea produselor
    @Query("SELECT * FROM products WHERE Location = :location AND Category = :categoryName")
    suspend fun getProductsByStoreAndCategory(location: String, categoryName: String): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int

    // FILTERS
    // a fost pus location pentru a avea numele magazinului in query din offersdev

    @Query("SELECT * FROM products WHERE Location = :storeName AND Category = :categoryName ORDER BY CurrentPrice ASC")
   suspend fun getProductsByLowestPrice(storeName: String, categoryName: String): List<Product>

    @Query("SELECT * FROM products WHERE Location = :storeName AND Category = :categoryName ORDER BY CurrentPrice DESC")
    suspend fun getProductsByHighestPrice(storeName: String, categoryName: String): List<Product>

    @Query("SELECT * FROM products WHERE Location = :storeName AND Category = :categoryName ORDER BY DiscountPercentage DESC")
   suspend fun getProductsByHighestDiscount(storeName: String, categoryName: String): List<Product>


    // discount

    @Query("SELECT * FROM products ORDER BY discountPercentage DESC ")
    suspend fun getTopDiscountedProducts(): List<Product>



    // stoc limitat
    @Query("SELECT * FROM products WHERE discountPercentage = 'Stoc limitat!*' OR 'comanzi 4, platesti 3' OR 'comanzi 2, platesti 1' LIMIT 30")
    suspend fun getLimitedStockProducts(): List<Product>

    // random products

    @Query("SELECT * FROM products ORDER BY RANDOM() LIMIT 30")
    suspend fun getRandomProducts(): List<Product>


    // search query

    @Query("SELECT * FROM products WHERE FullTitle LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<Product>


}




