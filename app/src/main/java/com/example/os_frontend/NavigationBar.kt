package com.example.os_frontend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.os_frontend.screens.cart.CartViewModel

data class NavigationItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun NavigationBar(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    // mutableStateOf e pentru a actualiza starea in timp real a unui element in ui
    // e folosit pentru a activa recompozitia, iar rememeber e utilizat pentru a retine valoarea
    // pe toata durata lifecycle lui
    var selectedItem by remember { mutableStateOf("Shop") }
    val items = listOf(
        NavigationItem("firstScreen", "Shop", Icons.Filled.Home),
        NavigationItem("secondScreen", "Explore", Icons.Filled.Search),
        NavigationItem("thirdScreen", "Wishlist", Icons.Filled.ShoppingCart),
        NavigationItem("fourthScreen", "Catalogs", Icons.Filled.List),
        NavigationItem("fifthScreen", "Account", Icons.Filled.Person)
    )

    val green = Color(0xFF227538)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val cartItems by cartViewModel.cartItems.collectAsState()
    var cartItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = cartItems) {
        cartItemCount = cartItems.sumOf { it.quantity }
    }

        NavigationBar(
            contentColor = Color.Black,
        ) {
            items.forEach { item ->
                val selected = selectedItem == item.label
                NavigationBarItem(
                    icon = {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (currentRoute == item.route) green
                                else Color.Black.copy(0.4f)
                            )
                            if (item.route == "thirdScreen" && cartItemCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .offset(x = 8.dp, y = -8.dp)
                                        .size(20.dp)
                                        .background(Color.Red, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute == item.route) green
                            else Color.Black.copy(0.4f)
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            selectedItem = item.label
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { screenRoute ->
                                    popUpTo(screenRoute) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
}