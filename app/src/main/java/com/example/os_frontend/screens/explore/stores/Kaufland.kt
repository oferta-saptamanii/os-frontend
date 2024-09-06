package com.example.os_frontend.screens.explore.stores

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.example.os_frontend.R
import com.example.os_frontend.firestore.Category
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.firestore.Store
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.explore.CategoryCard
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import kotlinx.coroutines.launch


fun filterStringsWithoutNumbers(categories: List<Category>): List<Category> {
    val regex = Regex("\\d")
    return categories.filter { !regex.containsMatchIn(it.name) }
}

fun filterStringsWithNumbers(categories: List<Category>): List<Category> {
    val regex = Regex("\\d")
    return categories.filter { regex.containsMatchIn(it.name) }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Kaufland(
    storeName: String,
    navController: NavController,
    fetchCategories: suspend (String) -> List<Category>,
) {
    var categories by rememberSaveable { mutableStateOf(listOf<Category>()) }
    var stores: Store
    var categoriesWithNumbers by rememberSaveable { mutableStateOf(listOf<Category>()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repo = FirestoreRepository(context)

    val electricBlue = Color(0xFF00FFFF)   // #00FFFF
    val brightOrange = Color(0xFFFFA500)   // #FFA500

    val fixedBrush = Brush.linearGradient(
        colors = listOf(electricBlue, brightOrange)
    )

    LaunchedEffect(Unit) {
        scope.launch {
            val allCategories = repo.fetchCategories("Kaufland")
            categories = filterStringsWithoutNumbers(allCategories)
            categoriesWithNumbers = filterStringsWithNumbers(allCategories)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toate categoriile",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            items(categoriesWithNumbers) { category ->
                KauflandCategorySpecialOffer(store = storeName, category = category, navController = navController)
            }

            items(categories) { category ->
                CategoryCard(
                    category = category,
                    gradient = listOf(Color(0xFF227538), Color(0xFF03DAC6)),
                    navController = navController,
                    storeName = storeName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun KauflandCategorySpecialOffer(store: String, category: Category, navController: NavController){
    ElevatedCard(
        onClick = {  navController.navigate("products/${store}/${category.name}") },
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
            .size(200.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(12.dp)

    ) {
          Column(
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.fillMaxSize()
          ) {
              Image(
                  painter = painterResource(id = R.drawable.ofertaspeciala),
                  contentDescription = null,
                  contentScale = ContentScale.Fit,
                  modifier = Modifier
                      .size(100.dp)
              )
              Text(
                  text = category.name,
                  fontFamily = GilroyFontFamily,
                  modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
              )
          }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreenforKaufland(navController: NavController, categoryName: String, cartViewModel: CartViewModel){
    var products by rememberSaveable { mutableStateOf(listOf<Product>()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repo = FirestoreRepository(context)
    var selectedProduct by remember { mutableStateOf<Product?>(null)}
    val sheetState = rememberModalBottomSheetState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        scope.launch {
            products = repo.fetchProducts("Kaufland", categoryName)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(
            snackBarHostState,
            snackbar = { data->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                )
            }
        )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductList(
                        product = product,
                        cartViewModel = cartViewModel,
                        onProductClick = {
                            selectedProduct = product
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        onProductBuy = {
                            scope.launch {
                                snackBarHostState.showSnackbar(
                                    "Produs adaugat in cos",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                }
            }

            if (selectedProduct != null) {
                BottomSheet(
                    product = selectedProduct!!,
                    cartViewModel = cartViewModel,
                    onClose = {
                        selectedProduct = null
                        scope.launch {
                            sheetState.hide()
                        }
                    }
                )
            }
        }
    }
}