package com.example.os_frontend.firestore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.os_frontend.firestore.category.Category
import com.example.os_frontend.firestore.category.CategoryDao
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.firestore.product.ProductDao
import com.example.os_frontend.firestore.store.Store
import com.example.os_frontend.firestore.store.StoreDao
import com.example.os_frontend.screens.cart.CartDao
import com.example.os_frontend.screens.cart.CartItem


@Database(
    entities = [
        Product::class,
        Category::class,
        Store::class,
        CartItem::class],
    version = 7 // version number / mitigation number
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun storeDao(): StoreDao
    abstract fun cartDao(): CartDao
}



object DatabaseProvider {
    @Volatile
    private var databaseInstance: AppDatabase? = null

    // provides an instance of the database using the Singleton pattern
    // Ensures that only one instance is created
    private fun provideDatabase(context: Context): AppDatabase {
        // return the existing database instance if it is already initialized
        return databaseInstance ?: synchronized(this) {
            val instance = databaseInstance
            if (instance != null) {
                instance // if another thread has already created the instance, return it
            } else {
                // create a new instance of the database
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-database"
                )
                    .fallbackToDestructiveMigration() // handles schema changes after recreating the database
                    .build()

                // assign the newly created instance
                databaseInstance = newInstance
                return newInstance
            }
        }
    }

    // provides an instance of ProductDao

    /*
    This is kinda of a manual dependency injection approach, instead of using Hilt or something else
    (because it didnt work) I am manually creating the dependencies inside the Database Provider
     */
    fun provideProductDao(context: Context): ProductDao {
        return provideDatabase(context).productDao()
    }

    fun provideCartDao(context: Context): CartDao {
        return provideDatabase(context).cartDao()
    }
}