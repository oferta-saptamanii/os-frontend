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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.firestore.Product
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.GrayBorderStroke

@Composable
fun ListContentCart(
    cartItems: List<CartItem>,
    cartViewModel: CartViewModel
) {
    val groupedCartItems = cartItems.groupBy { it.product.StoreName }
    var totalOverallPrice by remember {mutableDoubleStateOf(0.0)}

    totalOverallPrice = groupedCartItems.values.flatten().sumOf { cartItem ->
        val quantity = cartItem.quantity
        val price = cartItem.product.CurrentPrice?.toDoubleOrNull() ?: 0.0
        price * quantity
    }

    Column(modifier = Modifier.fillMaxWidth()) {
            if(cartItems.isNotEmpty()){
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
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
                        fontSize = 18.sp,
                    )
                    Image(
                        modifier = Modifier
                            .clickable {
                                cartViewModel.removeAllCartItems()
                            },
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        colorFilter = ColorFilter.tint(color = Color.DarkGray)
                    )
                }
        }


        if (cartItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spațiu între grupuri
            ) {
                groupedCartItems.forEach { (storeName, itemsInStore) ->
                    // Calculează totalul pentru fiecare magazin
                    val storeTotalPrice = itemsInStore.sumOf { cartItem ->
                        val quantity = cartItem.quantity
                        val price = cartItem.product.CurrentPrice?.toDoubleOrNull() ?: 0.0
                        price * quantity
                    }
                    item {
                        Text(
                            text = storeName,
                            fontFamily = GilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    // Afișează produsele din magazin
                    items(itemsInStore) { cartItem ->
                        ContentCart(cartItem, cartViewModel)
                    }

                    // Afișează totalul pentru magazin
                    item {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Total $storeName: ${"%.2f".format(storeTotalPrice)} lei",
                            fontFamily = GilroyFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            fontSize = 18.sp,
                        )
                    }

                    // Separator între grupuri
                    item {
                        HorizontalDivider(modifier = Modifier.height(1.dp), color = GrayBorderStroke)
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
        HorizontalDivider(modifier = Modifier.height(1.dp), color = GrayBorderStroke)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            AsyncImage(
                model = cartItem.product.Image,
                contentDescription = cartItem.product.FullTitle,
                modifier = Modifier.size(width = 64.dp, height = 64.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = cartItem.product.FullTitle,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Black,
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
                                    cartViewModel.removeFromCartByOne(cartItem.product)
                                }
                                .size(8.dp)
                                .background(Color.Red, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Decrease quantity"
                            )
                        }
                    } else if (cartItem.quantity == 1) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    cartViewModel.removeFromCart(cartItem.product)
                                }
                                .size(8.dp)
                                .background(Color.Red, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Delete"
                            )
                        }
                    }


                    Text(
                        text = "${cartItem.quantity}",
                        fontFamily = GilroyFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Black
                    )

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                cartViewModel.addToCart(cartItem.product)
                            }
                            .size(8.dp)
                            .background(Color.Green, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Increase quantity"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))

            val price = cartItem.product.CurrentPrice?.toFloatOrNull() ?: 0f
            // aici da convert de la currentprice la float pt ca e string
            // si daca e null va scrie 0

            val totalPrice = price * cartItem.quantity


            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp),
                text = "${"%.2f".format(totalPrice)} lei",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = 18.sp,
            )
        }
    }
}

