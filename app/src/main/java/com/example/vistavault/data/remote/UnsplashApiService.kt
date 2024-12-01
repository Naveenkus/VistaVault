package com.example.vistavault.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import com.example.vistavault.util.Constants.APT_KEY


interface UnsplashApiService {
    @Headers("Authorization: Client-ID $APT_KEY")
    @GET("/photos")
    suspend fun getEditorialFeedImages() : String
}