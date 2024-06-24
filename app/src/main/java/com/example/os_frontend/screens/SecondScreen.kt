package com.example.os_frontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SecondScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Yellow))
    {
        Text("Second Screen")
        Button(onClick = { /*TODO*/ }) {
        }
    }
}

@Preview
@Composable
fun SecondScreenPreview() {
    SecondScreen()
}