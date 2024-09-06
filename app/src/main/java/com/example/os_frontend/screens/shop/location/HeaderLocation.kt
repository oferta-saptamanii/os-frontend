package com.example.os_frontend.screens.shop.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.os_frontend.R
import com.example.os_frontend.ui.theme.GilroyFontFamily
import com.example.os_frontend.ui.theme.GrayThirdTextColor


@Composable
fun HeaderLocation(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_nectar),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = GrayThirdTextColor
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Romania",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = GrayThirdTextColor
            )
        }
    }
}