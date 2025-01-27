package com.example.vistavault.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageSearchResult(
//    @SerialName("results")
    val results: List<UnsplashImageDto>,
    val total: Int,
//    @SerialName("total_pages")
    val total_pages: Int
)