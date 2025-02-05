package com.example.os_frontend.firestore

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.os_frontend.firestore.category.Category
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.firestore.store.Store
import com.example.os_frontend.screens.catalogs.Catalog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirestoreRepository(context: Context) {

    private val db = FirebaseFirestore.getInstance()
    private val roomDb = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app-database"
    )
        .fallbackToDestructiveMigration()
        .build()


    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

    }


    suspend fun fetchAll(): List<Store> = withContext(Dispatchers.IO) {
        val storeList = mutableListOf<Store>()

        try {
            val storesSnapshot = db.collection("OffersDev").get().await()
            for (storeDocument in storesSnapshot.documents) {
                val storeName = storeDocument.id
                val store = Store(name = storeName)

                val categoriesSnapshot = db.collection("OffersDev/$storeName/categories").get().await()
                val categoryList = mutableListOf<Category>()

                for (categoryDocument in categoriesSnapshot.documents) {
                    val categoryName = categoryDocument.id
                    val productImg = categoryDocument.getString("ProductImg") ?: ""
                    val category = Category(name = categoryName, ProductImg = productImg, storeName = storeName)

                    val productsSnapshot = db.collection("OffersDev/$storeName/categories/$categoryName/products").get().await()
                    val productList = productsSnapshot.documents.mapNotNull { document ->
                        document.toObject(Product::class.java)?.apply {
                            this.Category = categoryName
                        }
                    }
                    categoryList.add(category)
                    roomDb.productDao().insertProducts(productList)
                }
                storeList.add(store)
                roomDb.categoryDao().insertCategories(categoryList)
            }

            roomDb.storeDao().insertStores(storeList)

        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching data: ${e.message}")
        }
        
        return@withContext storeList
    }

    suspend fun hasLocalData(): Boolean {
        return withContext(Dispatchers.IO) {
            val productCount = roomDb.productDao().getProductCount()
            productCount > 0
        }
    }



    suspend fun fetchProducts(categoryName: String): List<Product> = withContext(Dispatchers.IO) {
        return@withContext roomDb.productDao().getProductsByCategory(categoryName)
    }

    suspend fun fetchCategories(storeName: String): List<Category> = withContext(Dispatchers.IO) {
        return@withContext roomDb.categoryDao().getCategoriesByStore(storeName)
    }

    suspend fun fetchStores(): List<Store> = withContext(Dispatchers.IO) {
        return@withContext roomDb.storeDao().getAllStores()
    }

    suspend fun fetchAllProducts(): List<Product> = withContext(Dispatchers.IO){
        return@withContext roomDb.productDao().getAllProducts()
    }

    suspend fun fetchDiscountedProducts(): List<Product> = withContext(Dispatchers.IO){
        return@withContext roomDb.productDao().getTopDiscountedProducts()
    }

    suspend fun fetchLimitedStock(): List<Product> = withContext(Dispatchers.IO){
        return@withContext roomDb.productDao().getLimitedStockProducts()
    }

    suspend fun fetchRandomProducts(): List<Product> = withContext(Dispatchers.IO){
        return@withContext roomDb.productDao().getRandomProducts()
    }

    // catalogs

    suspend fun fetchCatalogs(storeName: String, city: String, store: String): List<Catalog> = withContext(Dispatchers.IO){
        val catalogPath =  "Catalogs/$storeName/cities/$city/stores/$store/catalogs"
        return@withContext try {
            val querySnapshot = db.collection(catalogPath).get().await()
            querySnapshot.documents.map { document ->
                document.toObject(Catalog::class.java) ?: Catalog()
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching data: ${e.message}")
            emptyList()
        }
    }


    suspend fun fetchCities(storeName: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val citiesPath = "Catalogs/$storeName/cities"
            val querySnapshot = db.collection(citiesPath).get().await()
            querySnapshot.documents.map { it.id }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching cities: ${e.message}")
            emptyList()
        }
    }

    suspend fun fetchStoresInCity(storeName: String, city: String): List<String> = withContext(Dispatchers.IO) {
        try {
            val storesPath = "Catalogs/$storeName/cities/$city/stores"
            val querySnapshot = db.collection(storesPath).get().await()
            querySnapshot.documents.map { it.id }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching stores in city: ${e.message}")
            emptyList()
        }
    }

}



