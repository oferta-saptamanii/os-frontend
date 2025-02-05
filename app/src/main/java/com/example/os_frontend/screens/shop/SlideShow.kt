package com.example.os_frontend.screens.shop

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.data.UiToolingDataApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.os_frontend.R
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.ui.theme.GilroyFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class, UiToolingDataApi::class)
@Composable
fun SlideShow(product: List<Product>) {

    val randomProducts = product.shuffled().take(25)
    val pagerState = rememberPagerState { randomProducts.size }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(randomProducts) {
        if (randomProducts.isNotEmpty()) {
            while (true) {
                yield()
                delay(2500)
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1) % pagerState.pageCount
                )
            }
        }
    }


    if (randomProducts.isNotEmpty()) {
        Column {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                PageItem(
                    product = randomProducts[page],
                    onProductClick = {
                        scope.launch {
                            sheetState.show()
                        }
                    },
                )
            }
        }
    }
}


@Composable
fun PageItem(
    product: Product,
    onProductClick: () -> Unit,
  //  cartViewModel: CartViewModel
) {

    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(12.dp)
            .height(150.dp)
            .clickable { onProductClick() }
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.elevatedCardElevation(12.dp),
    ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White),
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween,
                          verticalAlignment = Alignment.CenterVertically
                   ) {
                       Text(
                           text = product.FullTitle,
                           fontFamily = GilroyFontFamily,
                           color = Color.Black,
                           maxLines = 3,
                           overflow = TextOverflow.Ellipsis,
                           fontSize = 16.sp,
                           modifier = Modifier
                               .padding(4.dp)
                               .weight(0.35f)
                               .align(Alignment.Bottom),
                           fontWeight = FontWeight.Medium
                       )
                       AsyncImage(
                           model = ImageRequest.Builder(LocalContext.current)
                               .data(product.Image)
                               .diskCachePolicy(CachePolicy.ENABLED)
                               .memoryCachePolicy(CachePolicy.DISABLED)
                               .crossfade(true)
                               .build(),
                           contentDescription = product.FullTitle,
                           contentScale = ContentScale.Fit,
                           modifier = Modifier.weight(0.65f)
                       )
                       product.CurrentPrice?.let {
                           Text(
                               text = "${product.CurrentPrice} lei",
                               fontWeight = FontWeight.Bold,
                               fontFamily = GilroyFontFamily,
                               color = Color.Red,
                               fontSize = 18.sp,
                               modifier = Modifier.align(Alignment.Bottom)
                           )
                       }
                   }
                    product.DiscountPercentage?.let { discount ->
                        val discountPattern = Regex("-?\\s?\\d{1,2}%")


                        if (discountPattern.matches(discount)) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color.Red)
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Text(
                                    text = discount,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = GilroyFontFamily,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                    val imagePainter = when (product.StoreName) {
                        "Kaufland" -> painterResource(id = R.drawable.kaufland)
                        "Penny" -> painterResource(id = R.drawable.penny)
                        "Lidl" -> painterResource(id = R.drawable.lidl)
                        "Auchan" -> painterResource(id = R.drawable.auchan)
                        "Profi" -> painterResource(id = R.drawable.profi)
                        "MegaImage" -> painterResource(id = R.drawable.megaimage)
                        else -> painterResource(id = R.drawable.ofertaspeciala)
                    }
                    Image(
                        painter = imagePainter,
                        contentDescription = "Store Logo",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.TopStart)
                    )

                }
            }
        }
    }