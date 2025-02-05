package com.example.os_frontend

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.os_frontend.screens.cart.CartViewModel
import com.example.os_frontend.utils.Menu_book

@SuppressLint("SupportAnnotationUsage")
@Stable
data class Item(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val hasNews: Boolean? = false
)



@SuppressLint("SuspiciousIndentation")
@Composable
fun NavigationBar(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    // mutableStateOf e pentru a actualiza starea in timp real a unui element in ui
    // e folosit pentru a activa recompozitia, iar rememeber e utilizat pentru a retine valoarea
    // pe toata durata lifecycle lui
    var selectedItem by rememberSaveable { mutableStateOf("Shop") }

    val green = Color(0xFF227538)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val cartItems by cartViewModel.cartItems.collectAsState()
    var cartItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = cartItems) {
        cartItemCount = cartItems.sumOf { it.quantity }
    }

    val navButtons = listOf(
        Item(
            title = "Shop",
            route = "firstscreen",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home

        ),
        Item(
            title = "Explore",
            route = "thirdscreen",
            selectedIcon = Icons.Filled.Menu,
            unselectedIcon = Icons.Outlined.Menu,
        ),
        Item(
            title = "Search",
            route = "secondscreen",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
        ),
        Item(
            title = "Wishlist",
            route = "fourthscreen",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            badgeCount = cartItemCount
        ),
        Item(
            title = "Catalogs",
            route = "fifthscreen",
            selectedIcon = Menu_book,
            unselectedIcon = Menu_book,
            hasNews = true
        )
    )
        NavigationBar(
            contentColor = Color.Black,
        ) {
            navButtons.forEach { item ->
                val selected = selectedItem == item.title
                NavigationBarItem(
                    icon = {
                        BadgedBox(
                            badge = {
                                if(item.badgeCount != null){
                                    Badge{
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if (item.hasNews == true){
                                    Badge()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if(selectedItem == item.title) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                            color = if (currentRoute == item.route) green
                            else Color.Black.copy(0.4f)
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            selectedItem = item.title
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