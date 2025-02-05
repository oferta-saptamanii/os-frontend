package com.example.os_frontend.screens.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.os_frontend.firestore.category.Category
import com.example.os_frontend.ui.theme.GilroyFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(categories: List<Category>, navController: NavController){

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 0.dp, end = 0.dp, top = 8.dp, bottom = 8.dp),
        shape = RectangleShape
    ){
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(categories){ category ->
                CategoryCard(category, navController)
            }
        }
    }

}

// FIXME: nu merge sa navigheze la products, doar daca intri pe product list de la explore si revii, merge
// FIXME: am adaugat un loading in viewmodel dar tot nu vrea sa mearga, daca scot ala si imi apar filtrele
// FIXME: si dau click pe vreun filtru, apar produsele cu filtrele pe ele dar inca nu stiu de ce 


@Composable
fun CategoryCard(category: Category, navController: NavController){
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(6.dp)
            .width(100.dp)
            .height(140.dp)
            .fillMaxSize()
            .clickable {
                navController.navigate("products/${category.name}")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(12.dp),
    ) {
        AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(category.ProductImg)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .crossfade(true)
                    .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxSize()
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = category.name,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp

            )
        }
    }
}
