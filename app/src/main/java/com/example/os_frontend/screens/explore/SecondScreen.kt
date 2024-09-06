package com.example.os_frontend.screens.explore

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.os_frontend.R
import com.example.os_frontend.firestore.AggregatedCategory
import com.example.os_frontend.firestore.Category
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.firestore.Store
import com.example.os_frontend.graph.Screen
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun SecondScreen(navController: NavController) {

    var stores by rememberSaveable { mutableStateOf(listOf<Store>()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val repo = FirestoreRepository(context)

    LaunchedEffect(Unit) {
        scope.launch {
            stores = repo.fetchStores()
        }
    }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            items(stores){ store->
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


@Composable
fun StoreCard(
    store: Store,
    navController: NavController,
    modifier: Modifier = Modifier
) {


    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(20.dp),
        modifier = modifier.clickable { navController.navigate(store.name) }
    ) {
        Row(
            modifier = Modifier
                .background(Brush.horizontalGradient(listOf(Color(0xFF227538), Color.White)))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = store.name,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            val imagePainter = when (store.name) {
                "Kaufland" -> painterResource(R.drawable.kaufland)
                "Lidl" -> painterResource(R.drawable.lidl)
                "Penny" -> painterResource(R.drawable.penny)
                "MegaImage" -> painterResource(R.drawable.megaimage)
                "Auchan" -> painterResource(R.drawable.auchan)
                "Profi" -> painterResource(R.drawable.profi)
                else -> painterResource(R.drawable.ofertaspeciala)
            }

            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
