package com.example.vistavault.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.vistavault.data.local.entity.UnsplashImageEntity
import com.example.vistavault.data.local.entity.UnsplashRemoteKeys
import com.example.vistavault.domain.model.UnsplashImage

@Dao
interface EditorialFeedDao {
    @Query("SELECT * FROM image_table")
    fun getAllEditorialFeedImages():PagingSource<Int, UnsplashImageEntity>

    @Upsert
    suspend fun insertEditorialFeedImages(images: List<UnsplashImageEntity>)

    @Query("DELETE FROM image_table")
    suspend fun deleteEditorialFeedImages()

    @Query("SELECT * FROM remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Upsert
    suspend fun insertAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}