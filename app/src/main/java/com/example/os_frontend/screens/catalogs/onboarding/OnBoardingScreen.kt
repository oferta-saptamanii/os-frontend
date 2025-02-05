package com.example.os_frontend.screens.catalogs.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.os_frontend.firestore.FirestoreRepository
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.GraySecondTextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
  storeName: String,
  navController: NavController
) {
    val items = OnBoardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(pageCount = { items.size })
    val context = LocalContext.current
    val repo = remember { FirestoreRepository(context) }

    var selectedCity by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var selectedStore by rememberSaveable {
        mutableStateOf<String?>(null)
    }


    var cityOptions by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    var storeOptions by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(pageState.currentPage) {
        when (pageState.currentPage) {
            0 -> cityOptions = repo.fetchCities(storeName)
            1 -> selectedCity?.let { city ->
                storeOptions = repo.fetchStoresInCity(storeName, city)
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(
            onBackClick = {
                if (pageState.currentPage + 1 > 1) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onSkipClick = {
                if (pageState.currentPage + 1 < items.size) scope.launch {
                    pageState.scrollToPage(items.size - 1)
                }
            }
        )

        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> {
                    OnBoardingItem(
                        items = items[page],
                        options = cityOptions,
                        selectedOption = selectedCity,
                        onOptionSelected = { selectedCity = it }
                    )
                }
                1 -> {
                    OnBoardingItem(
                        items = items[page],
                        options = storeOptions,
                        selectedOption = selectedStore,
                        onOptionSelected = { selectedStore = it }
                        )
                }
            }
        }

        var showDialog by remember { mutableStateOf(false) }

        BottomSection(size = items.size, index = pageState.currentPage) {
            if (pageState.currentPage + 1 < items.size) {
                scope.launch {
                    pageState.scrollToPage(pageState.currentPage + 1)
                }
            } else {
                if (!selectedCity.isNullOrEmpty() && !selectedStore.isNullOrEmpty()) {
                    navController.navigate("catalogs/$storeName/$selectedCity/$selectedStore")
                } else {
                    showDialog = true
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Eroare", fontSize = 18.sp, color = Color.Black) },
                text = { Text("Selecteaza magazinul sau orasul pentru a continua!") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Ok")
                    }
                }
            )
        }

    }
}



    @Composable
fun OnBoardingItem(
        items: OnBoardingItems,
        options: List<String>,
        selectedOption: String?,
        onOptionSelected: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = items.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = items.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )

        Dropdown(
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val currentSelection = selectedOption ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } ,
         modifier = Modifier.width(IntrinsicSize.Max)
    ) {
        TextField(
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .height(55.dp)
                .menuAnchor()
                .clip(RoundedCornerShape(16.dp))
                .clickable { expanded = true },
            placeholder = {
                Text(
                    text = "Select an option",
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = GraySecondTextColor,
                    fontSize = 14.sp
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Black,
                fontSize = 14.sp
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

