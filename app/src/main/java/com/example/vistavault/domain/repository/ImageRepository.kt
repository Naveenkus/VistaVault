package com.example.vistavault.domain.repository

import com.example.vistavault.domain.model.UnsplashImage

interface ImageRepository {

    suspend fun getEditorialFeedImages() : List<UnsplashImage>
}