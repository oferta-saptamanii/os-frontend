package com.example.os_frontend.firestore

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class Subcategory(val name: String, val products: List<Product>)
data class AggregatedCategory(val name: String, val subcategories: List<Subcategory>)

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

    suspend fun resetDatabase() = withContext(Dispatchers.IO){
        roomDb.clearAllTables()
    }

    suspend fun fetchAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        val products = roomDb.productDao().getAllProducts()
        if (products.isNotEmpty()) {
            return@withContext products
        } else {

            val productList = mutableListOf<Product>()
            val storesSnapshot = db.collection("Stores").get().await()
            for (storeDocument in storesSnapshot.documents) {
                val storeName = storeDocument.id
                val categoriesSnapshot = db.collection("Stores/$storeName/categories").get().await()
                for (categoryDocument in categoriesSnapshot.documents) {
                    val categoryName = categoryDocument.id
                    val productsSnapshot = db.collection("OffersDev/$storeName/categories/$categoryName/products").get().await()
                    val categoryProducts = productsSnapshot.documents.mapNotNull { document ->
                        document.toObject(Product::class.java)?.apply {
                            this.Category = categoryName
                        }
                    }
                    productList.addAll(categoryProducts)
                }
            }
            roomDb.productDao().insertProducts(productList)
            return@withContext productList
        }
    }



    suspend fun fetchProducts(storeName: String, categoryName: String): List<Product> = withContext(Dispatchers.IO) {

        val existingProducts = roomDb.productDao().getProductsByCategory(categoryName)
        if (existingProducts.isNotEmpty()) {
            Log.d("firestore", "a intrat in iful in care se verifica daca lista e goala or not")
            return@withContext existingProducts
        } else {

            val productsPath = "OffersDev/$storeName/categories/$categoryName/products"
            try {
                val querySnapshot = db.collection(productsPath).get().await()
                val products = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(Product::class.java)?.apply {
                        this.Category = categoryName
                    }
                }
                roomDb.productDao().insertProducts(products)

                val categoryId = roomDb.categoryDao().getCategoryIdByName(categoryName)
                if (categoryId != null) {
                    val crossRefs = products.map { product ->
                        CategoryProductCrossRef(categoryId, product.id)
                    }
                    roomDb.categoryProductCrossRefDao().insertCategoryProductCrossRefs(crossRefs)
                }

                return@withContext products
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching products", e)
                emptyList()
            }
        }
    }


    suspend fun fetchCategories(storeName: String): List<Category> = withContext(Dispatchers.IO) {

        val existingCategories = roomDb.categoryDao().getCategoriesByStore(storeName)
        if (existingCategories.isNotEmpty()) {
            return@withContext existingCategories
        } else {
            val categoriesPath = "OffersDev/$storeName/categories"
            try {
                val querySnapshot = db.collection(categoriesPath).get().await()
                val categories = querySnapshot.documents.mapNotNull { document ->
                    val categoryName = document.id
                    val productImg = document.getString("ProductImg") ?: ""
                    Category(name = categoryName, ProductImg = productImg, storeName = storeName)
                }

                roomDb.categoryDao().insertCategories(categories)


                val storeId = roomDb.storeDao().getStoreIdByName(storeName)
                if (storeId != null) {

                    val storeCategoryCrossRefs = categories.map { category ->
                        StoreCategoryCrossRef(storeId, category.id)
                    }
                    roomDb.storeCategoryCrossRefDao().insertStoreCategoryCrossRefs(storeCategoryCrossRefs)
                }

                return@withContext categories
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching categories", e)
                emptyList()
            }
        }
    }




    suspend fun fetchStores(): List<Store> = withContext(Dispatchers.IO) {

        val stores = roomDb.storeDao().getAllStores()
        if (stores.isNotEmpty()) {
            return@withContext stores
        } else {

            val storesPath = "OffersDev"
            try {
                val querySnapshot = db.collection(storesPath).get().await()
                val storesList = querySnapshot.documents.mapNotNull { document ->

                    val storeName = document.id
                    Store(name = storeName)
                }
                roomDb.storeDao().insertStores(storesList)

                return@withContext storesList
            } catch (e: Exception) {
                Log.e("Firestore", "Error fetching stores", e)
                emptyList()
            }
        }
    }

}



