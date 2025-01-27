package com.example.vistavault.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vistavault.presentation.FullImageScreen.FullImageScreen
import com.example.vistavault.presentation.FullImageScreen.FullImageViewModel
import com.example.vistavault.presentation.ProfileScreen.ProfileScreen

import com.example.vistavault.presentation.SearchScreen.SearchScreen
import com.example.vistavault.presentation.SearchScreen.SearchScreenViewModel
import com.example.vistavault.presentation.favouritescreen.FavoritesViewModel
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
            val searchScreeviewModel : SearchScreenViewModel = hiltViewModel()
            val searchedImages = searchScreeviewModel.searchImages.collectAsLazyPagingItems()
            val favoriteImageIds by searchScreeviewModel.favoriteImagesIds.collectAsStateWithLifecycle()
            SearchScreen(
                onBackClick = { navController.navigateUp() },
                searchedImages = searchedImages,
                favoriteImageIds = favoriteImageIds,
                snackbarHostState = snackbarHostState,
                snackbarEvent = searchScreeviewModel.snackbarEvent,
                onImageClick = {imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearch = { searchScreeviewModel.searchImages(it) },
                onToggleFavoriteStatus = { searchScreeviewModel.toggleFavoriteStatus(it)}
            )
        }
        composable<Routes.FavouritesScreen> {
            val favoritesViewModel : FavoritesViewModel = hiltViewModel()
            val favoriteImages = favoritesViewModel.favoriteImage.collectAsLazyPagingItems()
            val favoritesImageIds by favoritesViewModel.favoriteImagesIds.collectAsStateWithLifecycle()
            FavouritesScreen (
                onBackClick = { navController.navigateUp() },
                favoriteImages = favoriteImages,
                favoriteImageIds = favoritesImageIds,
                snackbarHostState = snackbarHostState,
                snackbarEvent = favoritesViewModel.snackbarEvent,
                onImageClick = {imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onToggleFavoriteStatus = { favoritesViewModel.toggleFavoriteStatus(it)},
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                scrollBehavior = scrollBehavior,
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