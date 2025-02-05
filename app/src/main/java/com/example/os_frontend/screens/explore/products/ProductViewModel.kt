package com.example.os_frontend.screens.explore.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.firestore.product.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productDao: ProductDao) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun fetchFilteredProducts(storeName: String, categoryName: String, filter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            val filteredProducts = when (filter) {
                "Cele mai ieftine" -> productDao.getProductsByLowestPrice(storeName, categoryName)
                "Cele mai scumpe" -> productDao.getProductsByHighestPrice(storeName, categoryName)
                "Reduceri" -> productDao.getProductsByHighestDiscount(storeName, categoryName)
                else -> productDao.getProductsByStoreAndCategory(storeName, categoryName)
            }
            _products.emit(filteredProducts)
            _isLoading.emit(false)
            Log.d("ProductViewModel", filteredProducts.toString())

        }
    }



    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResult: StateFlow<List<Product>> = _searchResults

    fun searchProducts(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val results = productDao.searchProducts(query)
            _searchResults.emit(results)
        }
    }
}
