package com.example.os_frontend

import android.annotation.SuppressLint
import android.media.Image
import android.view.animation.OvershootInterpolator
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.ItemStyle
import com.example.os_frontend.ui.theme.Green

@SuppressLint("SupportAnnotationUsage")
@Stable
data class Item(
    val title: String,
    val route: String,
    val icon: ImageVector,
)

val navButtons = listOf(
    Item(
        title = "Shop",
        route = "firstScreen",
        icon = Icons.Default.Home,
    ),
    Item(
        title = "Explore",
        route = "secondScreen",
        icon = Icons.Default.Search,
    ),
    Item(
        title = "Wishlist",
        route = "thirdScreen",
        icon = Icons.Default.ShoppingCart,
    ),
    Item(
        title = "Catalogs",
        route = "fourthScreen",
        icon = Icons.Default.Menu,
    ),
    Item(
        title = "Account",
        route = "fifthScreen",
        icon = Icons.Default.Person,
    ),
)

@Composable
fun AnimNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedItem = navButtons.indexOfFirst { it.route == currentRoute }

    AnimatedBottomBar(
        selectedItem = selectedItem,
        itemSize = navButtons.size,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        navButtons.forEachIndexed { index, navButton ->
            val selected = currentRoute == navButton.route
            BottomBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != navButton.route) {
                        navController.navigate(navButton.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                imageVector = navButton.icon,
                label = navButton.title,
                itemStyle = ItemStyle.STYLE2,
                iconColor = if (selected) Color.Black else Color.White,
                textColor = if (selected) Color.Black else Color.White,
                activeIndicatorColor = Color.White,
            )
        }
    }
}
