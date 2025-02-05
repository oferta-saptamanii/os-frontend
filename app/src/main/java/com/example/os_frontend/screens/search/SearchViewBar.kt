package com.example.os_frontend.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.os_frontend.R
import com.example.os_frontend.firestore.product.Product
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.screens.explore.products.ProductViewModel
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.GraySecondTextColor
import com.example.os_frontend.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        modifier = modifier
            .padding(14.dp)
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(16.dp)),
        value = query,
        placeholder = {
           Text(
               text = "Search for products...",
               fontFamily = GilroyFontFamily,
               fontWeight = FontWeight.SemiBold,
               color = GraySecondTextColor,
               fontSize = 14.sp
           )
        },
        onValueChange = onQueryChange,
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Black,
            fontSize = 14.sp
        ),
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(Icons.Default.Search, contentDescription = "")
            }
        }
    )
}


@Composable
fun SearchScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    var query by remember { mutableStateOf("") }
    val searchResults by productViewModel.searchResult.collectAsState()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        SearchViewBar(
            query = query,
            onQueryChange = {
                query = it
                productViewModel.searchProducts(query)
                            },
            onSearch = {
                productViewModel.searchProducts(query)
            },
            modifier = Modifier.focusRequester(focusRequester)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (query.isEmpty()) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.empty_state_search),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }
            } else if (searchResults.isEmpty()) {
                    item {
                        Text("No products found for \"$query\"", color = Color.Gray)
                    }
                } else {
                    items(searchResults) { product ->
                        ProductItem(product, cartViewModel)
                        HorizontalDivider(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
}



@Composable
fun ProductItem(
    product: Product,
    cartViewModel: CartViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(1f)
        ) {
            Box{
                AsyncImage(
                    model = product.Image,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
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
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.FullTitle,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (!product.OriginalPrice.isNullOrBlank() && product.OriginalPrice != "0") {
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
        }

        IconButton(
            onClick = {
                cartViewModel.addToCart(product)
            },
            modifier = Modifier
                .size(36.dp)
                .background(Green, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

