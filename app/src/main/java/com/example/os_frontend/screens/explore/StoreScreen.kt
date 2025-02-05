package com.example.os_frontend.screens.explore

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.store.Store
import com.example.os_frontend.screens.components.StoreCard
import com.example.os_frontend.screens.explore.products.ProductViewModel

@Composable
fun StoreScreen(navController: NavController, productViewModel: ProductViewModel) {

    var stores by rememberSaveable { mutableStateOf(listOf<Store>()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repo = FirestoreRepository(context)

    LaunchedEffect(Unit) {
            stores = repo.fetchStores()
    }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(stores.filter { store-> store.name != "Aggregated" }){ store->
                StoreCard(
                    store = store,
                    navController = navController,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
}


