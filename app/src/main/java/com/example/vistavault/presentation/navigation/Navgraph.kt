package com.example.vistavault.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.Image
import com.example.vistavault.presentation.FullImageScreen.FullImageScreen
import com.example.vistavault.presentation.FullImageScreen.FullImageViewModel
import com.example.vistavault.presentation.ProfileScreen.ProfileScreen

import com.example.vistavault.presentation.SearchScreen.SearchScreen
import com.example.vistavault.presentation.favouritescreen.FavouritesScreen
import com.example.vistavault.presentation.homescreen.HomeScreen
import com.example.vistavault.presentation.homescreen.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
    scrollBehavior : TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            val viewModel : HomeScreenViewModel = hiltViewModel()
            HomeScreen(
                modifier = modifier,
                snackbarHostState = snackbarHostState,
                snackbarEvent = viewModel.snackbarEvent,
                scrollBehavior = scrollBehavior,
                images = viewModel.images,
                onImageClick = {imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId)) },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavouritesScreen) }
            )
        }
        composable<Routes.SearchScreen> {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FavouritesScreen> {
            FavouritesScreen (
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FullImageScreen> {
            val fullImageViewModel : FullImageViewModel = hiltViewModel()
            FullImageScreen(
                snackbarHostState = snackbarHostState,
                snackbarEvent = fullImageViewModel.snackbarEvent,
                image = fullImageViewModel.image,
                onBackClick = { navController.navigateUp() },
                onPhotographerNameClick = {profileLink ->
                    navController.navigate(Routes.ProfileScreen(profileLink))},
                onImageDownloadClick = {url, title ->
                    fullImageViewModel.downloadImage(url, title)
                }
            )
        }
        composable<Routes.ProfileScreen> {backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                ProfileLink = profileLink,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}