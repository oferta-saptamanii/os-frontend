package com.example.os_frontend.screens.catalogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.os_frontend.R
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.Green
import kotlin.math.absoluteValue

val stores = listOf(
   "Kaufland" to  R.drawable.kauflandcard,
    "Lidl" to R.drawable.lidlcard,
    "Profi" to R.drawable.proficard,
   "Penny" to  R.drawable.pennycard,
   "Auchan" to  R.drawable.auchancard,
   "MegaImage" to R.drawable.megacard,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoresScreen(navController: NavController){
    val pagerState = rememberPagerState(initialPage = 0) {
        stores.size
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
           text = stores[pagerState.currentPage].first,
            modifier = Modifier.padding(20.dp),
            style = TextStyle(fontSize = 40.sp),
            fontFamily = GilroyFontFamily
        )
        
        HorizontalPager(state = pagerState, contentPadding = PaddingValues(50.dp)) { index->
            CardContent(
                index = index,
                pagerState = pagerState,
                onCardClick = {
                    val storeName = stores[index].first
                    navController.navigate("onboarding_screen/$storeName")
                })
        }
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            stores.forEachIndexed { index, _ ->
                DotIndicator(
                    isSelected = index == pagerState.currentPage,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardContent(index: Int, pagerState: PagerState, onCardClick: () -> Unit){

    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
    ElevatedCard(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .padding(2.dp)
            .graphicsLayer {
                lerp(
                    start = 0.85f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }
                alpha = lerp(
                    start = 0.5f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                ).value
            }
            .height(300.dp)
            .background(Color.Transparent)
            .clickable { onCardClick() }
    ) {
        Image(
            painter = painterResource(stores[index].second),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DotIndicator(isSelected: Boolean,  modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(if (isSelected) 12.dp else 8.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) Green else Color.Gray
            )
    )
}
