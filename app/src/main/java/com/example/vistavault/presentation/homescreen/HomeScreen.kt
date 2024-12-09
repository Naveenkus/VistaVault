package com.example.vistavault.presentation.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.vistavault.data.remote.dto.UnsplashImageDto
import com.example.vistavault.domain.UnsplashImage


@Composable
fun HomeScreen(
    images : List<UnsplashImage>
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        images.forEach { image ->
            Text(text = image.id)
            Text(text = image.imageUrlRegular)
            Text(text = image.photographerName)
            Text(text = "${image.width}x${image.height}")
            Divider(
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}