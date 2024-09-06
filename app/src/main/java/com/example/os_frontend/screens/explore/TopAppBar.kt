package com.example.os_frontend.screens.explore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.relay.compose.ColumnScopeInstanceImpl.weight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController? = null) {
    val currentBackStackEntry by navController!!.currentBackStackEntryAsState()
    val title = when (currentBackStackEntry?.destination?.route) {
        "categories" -> "Lidl Categories"
        "products/{categoryName}" -> {
            val categoryName = currentBackStackEntry!!.arguments?.getString("categoryName")
            "$categoryName"
        }
        else -> "My App"
    }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (navController?.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}