package com.example.vistavault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vistavault.data.local.entity.FavouriteImageEntity
import com.example.vistavault.data.local.entity.UnsplashImageEntity
import com.example.vistavault.data.local.entity.UnsplashRemoteKeys

@Database(
    entities = [FavouriteImageEntity::class, UnsplashImageEntity::class, UnsplashRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class VistaVaultDatabase: RoomDatabase() {

    abstract fun favoriteImageDao(): FavoriteImagesDao
    abstract fun EditorialFeedDao(): EditorialFeedDao
}