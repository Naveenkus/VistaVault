package com.example.vistavault

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.vistavault.domain.model.NetworkStatus
import com.example.vistavault.domain.repository.NetworkConnectivityObserver
import com.example.vistavault.presentation.components.NetworkStatusBar
import com.example.vistavault.presentation.navigation.NavGraph
import com.example.vistavault.presentation.theme.CustomGreen
import com.example.vistavault.presentation.theme.VistaVaultTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityObserver: NetworkConnectivityObserver
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val status by connectivityObserver.networkStatus.collectAsState()
            var showMessageBar by rememberSaveable{ mutableStateOf(false) }
            var message by rememberSaveable { mutableStateOf("") }
            var backgroundColor by remember { mutableStateOf(Color.Red) }

            LaunchedEffect(key1 = status) {
                when(status){
                    NetworkStatus.Connected -> {
                        message = "Connected to Internet"
                        backgroundColor = CustomGreen
                        delay(2000)
                        showMessageBar = false
                    }
                    NetworkStatus.Disconnected -> {
                        showMessageBar = true
                        message = "No Internet Connection"
                        backgroundColor = Color.Red
                    }
                }
            }

            VistaVaultTheme {
                val navController = rememberNavController()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold (
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    bottomBar = {
                        NetworkStatusBar(
                            modifier = Modifier,
                            showMessageBar = showMessageBar,
                            message = message,
                            backgroundColor = backgroundColor
                        )
                    }
                ){
                    NavGraph(
                        modifier = Modifier,
                        navController = navController,
                        scrollBehavior = scrollBehavior,
                        snackbarHostState = snackbarHostState
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