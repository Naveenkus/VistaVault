package com.example.vistavault.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.vistavault.domain.model.UnsplashImage

@Composable
fun ImageCard(
    modifier: Modifier,
    image : UnsplashImage?,
    isFavourite: Boolean,
    onToggleFavouriteStatus: () -> Unit
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data( image?.imageUrlSmall)
        .crossfade(true)
        .build()
    val aspectRatio : Float by remember {
        derivedStateOf { (image?.width?.toFloat() ?:1f )/( image?.height?.toFloat() ?:1f)}
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .then(modifier)
    ) {
        Box(){
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            FavouriteButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                isFavourite = isFavourite,
                onClick = onToggleFavouriteStatus
            )
        }
    }
}

@Composable
fun FavouriteButton(
    modifier: Modifier,
    isFavourite: Boolean,
    onClick: () -> Unit
){
    FilledIconToggleButton(
        modifier = modifier,
        checked = isFavourite,
        onCheckedChange = { onClick() },
        colors = IconButtonDefaults.filledIconToggleButtonColors(
            containerColor = Color.Transparent
        )
    ){
        if (isFavourite) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
        } else {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
        }
    }
}