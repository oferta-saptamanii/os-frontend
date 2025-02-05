package com.example.os_frontend.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.os_frontend.R
import com.example.os_frontend.firestore.store.Store
import com.example.os_frontend.ui.theme.GilroyFontFamily

@Composable
fun StoreCard(
    store: Store,
    navController: NavController,
    modifier: Modifier = Modifier
) {


    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
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
