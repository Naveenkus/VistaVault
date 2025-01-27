package com.example.vistavault.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vistavault.data.local.entity.FavouriteImageEntity

@Database(
    entities = [FavouriteImageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VistaVaultDatabase: RoomDatabase() {

    abstract fun favoriteImageDao(): FavoriteImagesDao
}