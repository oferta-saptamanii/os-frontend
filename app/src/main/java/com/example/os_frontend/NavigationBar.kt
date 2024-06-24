package com.example.os_frontend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class NavigationItem(val route: String, val label: String, val icon: ImageVector)

@Composable
fun NavigationBar(
    navController: NavController
){
    // mutableStateOf e pentru a actualiza starea in timp real a unui element in ui
    // e folosit pentru a activa recompozitia, iar rememeber e utilizat pentru a retine valoarea
    // pe toata durata lifecycle lui
    var selectedItem by remember { mutableStateOf("Shop") }
    val items = listOf(
        NavigationItem("firstScreen","Shop", Icons.Filled.Home),
        NavigationItem("secondScreen","Explore", Icons.Filled.Search),
        NavigationItem("thirdScreen","Wishlist", Icons.Filled.ShoppingCart),
        NavigationItem("fourthScreen","Catalogs", Icons.Filled.List)
    )

    val green = Color(0xFF227538)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBarDestination = items.any { it.route == currentRoute }

    if (bottomBarDestination) {
        NavigationBar(
            contentColor = Color.Black,
        ) {
            items.forEach { item ->
                val selected = selectedItem == item.label
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (currentRoute == item.route) green
                                else Color.Black.copy(0.4f)
                            )
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
                            if(!selected) {
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
    }
