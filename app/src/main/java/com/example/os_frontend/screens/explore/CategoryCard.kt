package com.example.os_frontend.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.Category
import com.example.os_frontend.ui.theme.GilroyFontFamily
import kotlin.math.max

private const val CategoryTextProportion = 0.55f
private val MinImageSize = 134.dp

@Composable
fun CategoryCard(
    category: Category,
    gradient: List<Color>,
    navController: NavController,
    storeName: String,
    modifier: Modifier = Modifier
){
    Layout(
        modifier = modifier
            .aspectRatio(1.45f)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Brush.horizontalGradient(gradient))
            .clickable { navController.navigate("products/${storeName}/${category.name}") },
        content = {
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            AsyncImage(
                model = category.ProductImg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    ) {
            measurables, constraints ->
        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))

        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2 // centrat vertical
            )
            imagePlaceable.placeRelative(
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2 // centrat vertical
            )
        }
    }
}