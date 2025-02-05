package com.example.os_frontend.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.os_frontend.R
import com.example.os_frontend.ui.theme.Black
import com.example.os_frontend.ui.theme.GilroyFontFamily


@Composable
fun EmptyContent(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(248.dp),
            painter = painterResource(R.drawable.img_empty_content),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Cos gol!",
            fontFamily = GilroyFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Black,
            textAlign = TextAlign.Center,
        )
    }
}