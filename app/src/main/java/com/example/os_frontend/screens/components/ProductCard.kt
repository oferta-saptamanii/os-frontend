package com.example.os_frontend.screens.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.os_frontend.R
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.Green
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductCard(
    product: Product,
    cartViewModel: CartViewModel,
    onProductClick: () -> Unit,
    onProductBuy: (Product) -> Unit
){
    val scope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(false) }



    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(12.dp)
            .width(174.dp)
            .height(265.dp)
            .fillMaxSize()
            .clickable { onProductClick() },
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

            Box{
                AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.Image)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .crossfade(true)
                            .build(),
                    contentDescription = product.FullTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentScale = ContentScale.Fit,
                    )
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
                                    .padding(4.dp),
                            )
                        }
                    }
                }
                val imagePainter = when(product.StoreName){
                    "Kaufland" -> painterResource(id = R.drawable.kaufland)
                    "Penny" -> painterResource(id= R.drawable.penny)
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
                        .size(26.dp)
                        .align(Alignment.TopStart)
                )

            }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product.FullTitle,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            // l am comentat quantityu ca nu am habar de ce pentru auchan si kaufland afiseaza haotic
                product.Quantity?.let {
                    Text(
                        text = it,
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 0.dp),
                        lineHeight = 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column{
                       if(product.OriginalPrice != null && product.OriginalPrice != "0"){
                           Text(
                               text = "${product.OriginalPrice} lei",
                               fontWeight = FontWeight.SemiBold,
                               fontFamily = GilroyFontFamily,
                               color = Color.Black,
                               fontSize = 12.sp,
                               textDecoration = TextDecoration.LineThrough
                           )

                           Text(
                               text = "${product.CurrentPrice} lei",
                               fontWeight = FontWeight.Bold,
                               fontFamily = GilroyFontFamily,
                               color = Color.Red,
                               fontSize = 18.sp
                           )
                       } else {
                           Spacer(modifier = Modifier.height(18.dp))
                           Text(
                               text = "${product.CurrentPrice} lei",
                               fontWeight = FontWeight.Bold,
                               fontFamily = GilroyFontFamily,
                               color = Color.Black,
                               fontSize = 18.sp
                           )
                       }
                    }

                    Button(
                        modifier = Modifier.size(46.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            disabledContainerColor = Green.copy(alpha = 0.5f),
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(10.dp),
                        enabled = !isChecked,
                        onClick = {
                            onProductBuy(product)
                            scope.launch{
                                isChecked = true
                                cartViewModel.addToCart(product)
                                delay(1000)
                                isChecked = false
                            }
                        }
                    )
                    {
                        Crossfade(targetState = isChecked, label = ""){ checked ->
                            if(checked){
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                        }

                    }
                }
            }
        }
    }


//@Preview
//@Composable
//fun ProductCardPreview(){
//    ProductCard(
//        Product(
//            1,
//            "",
//            "",
//            "21.99",
//            "",
//            "Catty Appetite Hrana pentru pisici",
//            "https://kaufland.media.schwarz/is/image/schwarz/5948308003437_RO_P?JGstbGVnYWN5LW9uc2l0ZS00JA==",
//            "",
//            "",
//            ""
//        ),
//        viewModel()
//    )
//}