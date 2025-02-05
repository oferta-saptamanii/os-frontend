package com.example.os_frontend.screens.cart

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.R
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.GrayBorderStroke

@Composable
fun ListContentCart(
    cartItems: List<CartItem>,
    cartViewModel: CartViewModel
) {
    val groupedCartItems = cartItems.groupBy { it.storeName }
    var totalOverallPrice by remember { mutableDoubleStateOf(0.0) }

    totalOverallPrice = cartItems.sumOf { it.price.toDoubleOrNull()?.times(it.quantity) ?: 0.0 }

    val expandStates = remember { mutableStateMapOf<String, Boolean>() }

    Column(modifier = Modifier.fillMaxWidth()) {
        if (cartItems.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Lista de cumpărături",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Preț total: ${"%.2f".format(totalOverallPrice)} lei",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = 18.sp
                )
                Image(
                    modifier = Modifier.clickable {
                        cartViewModel.removeAllCartItems()
                    },
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(Color.DarkGray)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                groupedCartItems.forEach { (storeName, itemsInStore) ->
                    val isExpanded = expandStates[storeName] ?: false
                    val totalStorePrice = itemsInStore.sumOf { it.price.toDoubleOrNull()?.times(it.quantity) ?: 0.0 }

                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            onClick = { expandStates[storeName] = !isExpanded },
                            elevation = CardDefaults.elevatedCardElevation(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val imagePainter = when (storeName) {
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
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Text(
                                        text = storeName,
                                        fontFamily = GilroyFontFamily,
                                        fontWeight = FontWeight.Medium,
                                        color = Black,
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Expand",
                                        tint = Color.DarkGray
                                    )
                                }

                                if (isExpanded) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    itemsInStore.forEach { cartItem ->
                                        ContentCart(cartItem, cartViewModel)
                                    }

                                    Text(
                                        modifier = Modifier.padding(top = 8.dp),
                                        text = "Total $storeName: ${"%.2f".format(totalStorePrice)} lei",
                                        fontFamily = GilroyFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = Black,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            EmptyContent()
        }
    }
}



@Composable
fun ContentCart(
    cartItem: CartItem,
    cartViewModel: CartViewModel
) {
    Column {
        HorizontalDivider(
            modifier = Modifier.height(1.dp),
            color = GrayBorderStroke
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            AsyncImage(
                model = cartItem.image,
                contentDescription = cartItem.name,
                modifier = Modifier.size(width = 64.dp, height = 64.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = cartItem.name,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Cantitate: ",
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Black
                    )

                    if (cartItem.quantity > 1) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    cartViewModel.decreaseQuantity(cartItem)
                                }
                                .background(Color.Red, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Decrease quantity",
                                tint = Color.White
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    cartViewModel.removeFromCart(cartItem)
                                }
                                .background(Color.Red, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Delete item",
                                tint = Color.White
                            )
                        }
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Black
                    )

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                cartViewModel.increaseQuantity(cartItem)
                            }
                            .background(Color.Green, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Increase quantity",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            val price = cartItem.price.toFloatOrNull() ?: 0f
            val totalPrice = price * cartItem.quantity

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                text = "${"%.2f".format(totalPrice)} lei",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = 18.sp
            )
        }
    }
}
