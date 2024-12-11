package com.example.vistavault

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vistavault.presentation.homescreen.HomeScreen
import com.example.vistavault.presentation.homescreen.HomeScreenViewModel
import com.example.vistavault.presentation.theme.VistaVaultTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistaVaultTheme {
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val viewModel = viewModel<HomeScreenViewModel> ()
                Scaffold (
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ){
                    HomeScreen(
                        modifier = Modifier,
                        scrollBehavior = scrollBehavior,
                        images = viewModel.images,
                        onImageClick = {},
                        onSearchClick = {},
                        onFABClick = {}
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VistaVaultTheme {

    }
}