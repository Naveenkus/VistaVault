package com.example.vistavault.presentation.homescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vistavault.R
import com.example.vistavault.data.remote.dto.UnsplashImageDto
import com.example.vistavault.domain.UnsplashImage
import com.example.vistavault.presentation.components.ImageCard
import com.example.vistavault.presentation.components.ImageVerticalGrid
import com.example.vistavault.presentation.components.VistaVaultTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    images : List<UnsplashImage>,
    onImageClick : (String) -> Unit,
    onSearchClick : () -> Unit,
    onFABClick : () -> Unit
){
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
                images = images,
                onImageClick = onImageClick,
                modifier = modifier
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
    }
}