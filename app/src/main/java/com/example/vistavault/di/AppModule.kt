package com.example.vistavault.di

import android.content.Context
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.data.repository.AndroidImageDownloader
import com.example.vistavault.data.repository.ImageRepositoryImpl
import com.example.vistavault.domain.repository.Downloader
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUnsplashApiService (): UnsplashApiService{
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(UnsplashApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository( apiService: UnsplashApiService ) : ImageRepository {
        return ImageRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAndroidImageDownloader(
        @ApplicationContext context : Context
    ): Downloader {
        return AndroidImageDownloader( context)
    }
}