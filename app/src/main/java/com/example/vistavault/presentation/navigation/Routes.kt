package com.example.vistavault.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object HomeScreen : Routes()
    @Serializable
    data object SearchScreen : Routes()
    @Serializable
    data object FavouritesScreen : Routes()
    @Serializable
    data class FullImageScreen (val imageId : String) : Routes()
    @Serializable
    data class ProfileScreen (val profileLink : String) : Routes()
}