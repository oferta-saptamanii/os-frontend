package com.example.os_frontend.screens.explore.stores

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.Green


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    product: Product,
    onClose: () -> Unit,
    cartViewModel: CartViewModel
){
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
                        colors = ButtonDefaults.buttonColors(Green),
                        onClick = { cartViewModel.addToCart(product) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Adauga in lista", color = Color.White)
                    }
                }
            }
        }
    }
}

