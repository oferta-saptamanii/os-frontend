package com.example.os_frontend.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.firestore.persistentCacheSettings
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    init {
        val db = FirebaseFirestore.getInstance()
        val settings = firestoreSettings {
            // use persistent disk cache
            setLocalCacheSettings(persistentCacheSettings {  })
        }
        db.firestoreSettings = settings
    }


    suspend fun fetchProducts(storeName: String, categoryName: String): List<Product> {
        val db = FirebaseFirestore.getInstance()
        val productsPath = "Offers/$storeName/categories/$categoryName/products"
        return try {
            val querySnapshot = db.collection(productsPath).get().await()
            querySnapshot.documents.map { document ->
                document.toObject(Product::class.java) ?: Product()
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching products", e)
            emptyList()
        }
    }

    suspend fun fetchCategories(storeName: String): List<Category> {
        val db = FirebaseFirestore.getInstance()
        val categoriesPath = "Offers/$storeName/categories"
        return try {
            val querySnapshot = db.collection(categoriesPath).get().await()
            querySnapshot.documents.map { document ->
                val categoryName = document.id
                val products = fetchProducts(storeName, categoryName)
                Category(name = categoryName, products = products)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching categories", e)
            emptyList()
        }
    }

    suspend fun fetchStores(): List<Store> {
        val db = FirebaseFirestore.getInstance()
        val storesPath = "Offers"
        return try {
            val querySnapshot = db.collection(storesPath).get().await()
            querySnapshot.documents.map { document ->
                val storeName = document.id
                val categories = fetchCategories(storeName)
                Store(name = storeName, categories = categories)
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching stores", e)
            emptyList()
        }
    }


}
