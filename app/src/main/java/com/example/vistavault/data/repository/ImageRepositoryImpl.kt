package com.example.vistavault.data.repository

import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.domain.repository.ImageRepository


class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
): ImageRepository {
    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
    }
}