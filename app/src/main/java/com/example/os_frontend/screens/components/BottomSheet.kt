package com.example.os_frontend.screens.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.Green
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    product: Product,
    onClose: () -> Unit,
    onProductBuy: (Product) -> Unit
){
    var isChecked by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier
            .height(600.dp),
        containerColor = Color.White,
    ) {
        Row(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Top
            ){
                Box{
                    AsyncImage(
                        model = product.Image,
                        contentDescription = product.FullTitle,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .padding(4.dp),

                        )
                    product.DiscountPercentage?.let {
                        Box(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(Color.Red)
                            .clip(RoundedCornerShape(12.dp))
                        ){
                            Text(
                                text = it,
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
                Spacer(modifier = Modifier.height(12.dp))
                product.AvailableDate?.let {
                    Text(
                        text = "Oferta valabila in perioada \n$it",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = GilroyFontFamily,
                        color = Black,
                        fontSize = 14.sp
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)){
                Text(
                    text = product.FullTitle,
                    color = Black,
                    fontFamily = GilroyFontFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    if(product.OriginalPrice != null && product.OriginalPrice != "0"){
                        Text(
                            text = "${product.OriginalPrice} lei",
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = GilroyFontFamily,
                            color = Color.Black,
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough
                        )

                        Text(
                            text = "${product.CurrentPrice} lei",
                            fontWeight = FontWeight.Bold,
                            fontFamily = GilroyFontFamily,
                            color = Color.Red,
                            fontSize = 22.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = "${product.CurrentPrice} lei",
                            fontWeight = FontWeight.Bold,
                            fontFamily = GilroyFontFamily,
                            color = Color.Black,
                            fontSize = 22.sp
                        )
                    }
                    product.Quantity?.let {
                        Text(
                            text = it,
                            fontFamily = GilroyFontFamily,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green,
                            disabledContainerColor = Green.copy(alpha = 0.5f),
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            scope.launch{
                                isChecked = true
                                onProductBuy(product)
                                delay(1000)
                                isChecked = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Crossfade(targetState = isChecked, label = "") { checked ->

                            val scale by animateFloatAsState(
                                targetValue = if (checked) 1.5f else 1f,
                                label = "Animation",
                                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                            )
                            if (checked) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.scale(scale)
                                )
                            } else {
                               Text(
                                      text = "Adauga in lista",
                                      color = Color.White
                               )
                            }
                        }
                    }
                }
            }
        }
    }
}
