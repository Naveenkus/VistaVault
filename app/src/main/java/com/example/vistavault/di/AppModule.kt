package com.example.vistavault.di

import android.content.Context
import androidx.room.Room
import com.example.vistavault.data.local.VistaVaultDatabase
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.data.repository.AndroidImageDownloader
import com.example.vistavault.data.repository.ImageRepositoryImpl
import com.example.vistavault.data.repository.NetworkConnectivityObserverImpl
import com.example.vistavault.domain.repository.Downloader
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.domain.repository.NetworkConnectivityObserver
import com.example.vistavault.util.Constants.BASE_URL
import com.example.vistavault.util.Constants.VISTA_VAULT_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    fun provideVistaVaultDatabase(
        @ApplicationContext context : Context
    ): VistaVaultDatabase {
        return Room
            .databaseBuilder(
                context,
                VistaVaultDatabase::class.java,
                VISTA_VAULT_DATABASE
            )
            .build()
    }
    @Provides
    @Singleton
    fun provideImageRepository(
        apiService: UnsplashApiService,
        database: VistaVaultDatabase
    ) : ImageRepository {
        return ImageRepositoryImpl(apiService, database)
    }

    @Provides
    @Singleton
    fun provideAndroidImageDownloader(
        @ApplicationContext context : Context
    ): Downloader {
        return AndroidImageDownloader( context)
    }


    @Provides
    @Singleton
    fun provideApplicationScope():CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver (
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context, scope)
    }
}