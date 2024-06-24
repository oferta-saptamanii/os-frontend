package com.example.os_frontend.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.Category
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.firestore.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(navController: NavController) {
    var stores by rememberSaveable { mutableStateOf(listOf<Store>()) }
    val scope = rememberCoroutineScope()
    val repo = FirestoreRepository()

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            stores = repo.fetchStores()
        }
    }

    Column {
        Text("Stores", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
        LazyColumn {
            items(stores) { store ->
                StoreItem(store = store)
            }
        }
    }
}

@Composable
fun StoreItem(store: Store) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { expanded = !expanded }
        .padding(16.dp)) {
        Text(store.name, style = MaterialTheme.typography.bodyLarge)
        if (expanded) {
            CategoryList(categories = store.categories)
        }
    }
}

@Composable
fun CategoryList(categories: List<Category>) {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        for (category in categories) {
            var expanded by remember { mutableStateOf(false) }
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)) {
                Text(category.name, style = MaterialTheme.typography.bodyMedium)
                if (expanded) {
                    ProductList(products = category.products)
                }
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    Column(modifier = Modifier.padding(start = 16.dp)) {
        for (product in products) {
            Text(product.FullTitle, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(4.dp))
            AsyncImage(model = product.Image, contentDescription = null)
        }
    }
}