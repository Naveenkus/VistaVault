package com.example.vistavault.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vistavault.R
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.presentation.components.ImageVerticalGrid
import com.example.vistavault.presentation.components.VistaVaultTopAppBar
import com.example.vistavault.presentation.components.ZoomedImageCard
import com.example.vistavault.presentation.util.SnackbarEvent
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    snackbarEvent: Flow<SnackbarEvent>,
    scrollBehavior: TopAppBarScrollBehavior,
    images : List<UnsplashImage>,
    onImageClick : (String) -> Unit,
    onSearchClick : () -> Unit,
    onFABClick : () -> Unit
){
    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    LaunchedEffect(key1 = true){
        snackbarEvent.collect {event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            VistaVaultTopAppBar(
                modifier = modifier,
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
            ImageVerticalGrid(
                modifier = modifier,
                images = images,
                onImageClick = onImageClick,
                onImageDragStart = {image ->
                    activeImage = image
                    showImagePreview = true
                },
                onImageDragEnd ={ showImagePreview = false }
            )
        }
        FloatingActionButton(
            modifier = Modifier.align(alignment = Alignment.BottomEnd).padding(24.dp),
            onClick = { onFABClick()}
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_account_tree),
                contentDescription = "Favourites",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        ZoomedImageCard(
            modifier = Modifier.padding(20.dp),
            isVisible = showImagePreview,
            image = activeImage
        )
    }
}