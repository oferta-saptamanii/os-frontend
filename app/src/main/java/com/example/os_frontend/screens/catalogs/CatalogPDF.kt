package com.example.os_frontend.screens.catalogs

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.rememberHorizontalPdfReaderState


@Composable
fun CatalogPDF(url: String) {
    val pdfState = rememberHorizontalPdfReaderState(
        resource = ResourceType.Remote(url),
        isZoomEnable = true
    )

    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 5f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .transformable(state = state)
    ) {
        HorizontalPDFReader(
            state = pdfState,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray.copy(alpha = 0.5f)
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Page: ${pdfState.currentPage}/${pdfState.pdfPageCount}",
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            }
        }
    }
}
