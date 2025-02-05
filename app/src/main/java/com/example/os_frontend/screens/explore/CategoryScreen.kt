package com.example.os_frontend.screens.explore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.category.Category
import com.example.os_frontend.screens.components.CategoryCard



@Composable
fun CategoryScreen(
    storeName: String,
    navController: NavController,
    fetchCategories: suspend (String) -> List<Category>,
    ){
    var categories by rememberSaveable { mutableStateOf(listOf<Category>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
            categories = fetchCategories(storeName)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories){ category ->
            CategoryCard(
                category = category,
                gradient = listOf(Color(0xFF227538), Color(0xFF03DAC6)),
                navController = navController,
                storeName = storeName,
                modifier = Modifier
                   // .weight(1f)// daca da ceva gresit, e de aici
                    .padding(8.dp)
            )
        }
    }
}


