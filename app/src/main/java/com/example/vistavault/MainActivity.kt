package com.example.vistavault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vistavault.presentation.homescreen.HomeScreen
import com.example.vistavault.presentation.homescreen.HomeScreenViewModel
import com.example.vistavault.presentation.theme.VistaVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VistaVaultTheme {
                val viewModel = viewModel<HomeScreenViewModel> ()
                HomeScreen(images = viewModel.images)
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