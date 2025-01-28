package com.example.vistavault.domain.repository

import androidx.paging.PagingData
import com.example.vistavault.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ImageRepository {

    fun getEditorialFeedImages() : Flow<PagingData<UnsplashImage>>

    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImages(query: String) : Flow<PagingData<UnsplashImage>>

    fun getAllFavoritesImages() : Flow<PagingData<UnsplashImage>>

    suspend fun toggleFavoriteStatus(image: UnsplashImage)

    fun getFavoriteImagesIds(): Flow<List<String>>
}