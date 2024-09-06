package com.example.os_frontend.screens.explore.stores

import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.Green
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductList(
    product: Product,
    cartViewModel: CartViewModel,
    onProductClick: () -> Unit,
    onProductBuy: (Product) -> Unit
){
    val scope = rememberCoroutineScope()
    var isChecked by remember { mutableStateOf(false) }


    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(12.dp)
            .width(174.dp)
            .height(260.dp)
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
                       model = product.Image,
                       contentDescription = product.FullTitle,
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(100.dp),
                       contentScale = ContentScale.Fit
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
                    overflow = TextOverflow.Ellipsis
                )
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
                                delay(2000)
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
