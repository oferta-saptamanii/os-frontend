package com.example.os_frontend.screens.catalogs

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.os_frontend.R
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.ui.theme.GilroyFontFamily

@Composable
fun CatalogsScreen(
    storeName: String,
    city: String,
    store: String,
    navController: NavController
){
    var catalogs by rememberSaveable { mutableStateOf(listOf<Catalog>()) }
    val context = LocalContext.current
    val repo = remember { FirestoreRepository(context) }


    LaunchedEffect(Unit) {
        catalogs = repo.fetchCatalogs(storeName, city, store)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(catalogs) { item ->
            CatalogCard(item, navController)
        }
    }
}

@Composable
fun CatalogCard(catalog: Catalog, navController: NavController){
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(12.dp)
            .width(174.dp)
            .height(265.dp)
            .fillMaxSize()
            .clickable {
                navController.navigate("catalog_pdf/${Uri.encode(catalog.Url)}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(12.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            if(catalog.Image != null){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(catalog.Image)
                        .crossfade(true)
                        .build(),
                    contentDescription = catalog.Name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ofertaspeciala),
                    contentDescription = catalog.Name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = catalog.Name,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            catalog.AvailableDate?.let {
                Text(
                    text = it,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Light,
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}