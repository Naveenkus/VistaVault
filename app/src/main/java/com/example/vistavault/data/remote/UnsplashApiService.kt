package com.example.vistavault.data.remote

import com.example.vistavault.data.remote.dto.UnsplashImageDto
import com.example.vistavault.data.remote.dto.UnsplashImageSearchResult
import retrofit2.http.GET
import retrofit2.http.Headers
import com.example.vistavault.util.Constants.APT_KEY
import retrofit2.http.Path
import retrofit2.http.Query


interface UnsplashApiService {
    @Headers("Authorization: Client-ID $APT_KEY")
    @GET("/photos")
    suspend fun getEditorialFeedImages() : List<UnsplashImageDto>

    @Headers("Authorization: Client-ID $APT_KEY")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : UnsplashImageSearchResult

    @Headers("Authorization: Client-ID $APT_KEY")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId : String
    ) : UnsplashImageDto
}