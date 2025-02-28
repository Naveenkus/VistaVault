package com.example.vistavault.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.vistavault.data.local.VistaVaultDatabase
import com.example.vistavault.data.mapper.toDomainModel
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.data.mapper.toFavouriteImageEntity
import com.example.vistavault.data.paging.EditorialFeedRemoteMediator
import com.example.vistavault.data.paging.SearchPagingSource
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalPagingApi::class)
class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService,
    private val database : VistaVaultDatabase
): ImageRepository {

    private val favoriteImagesDao = database.favoriteImageDao()
    private val edittorialImagesDao = database.EditorialFeedDao()
    override  fun getEditorialFeedImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = EditorialFeedRemoteMediator(unsplashApi, database),
            pagingSourceFactory = { edittorialImagesDao.getAllEditorialFeedImages()}
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { SearchPagingSource(query, unsplashApi)}
        ).flow
    }

    override fun getAllFavoritesImages(): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { favoriteImagesDao.getAllFavoriteImages()}
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {
        val isFavorite = favoriteImagesDao.isImageFavorite(image.id)
        val favoriteImage = image.toFavouriteImageEntity()
        if (isFavorite){
            favoriteImagesDao.deleteFavoriteImage(favoriteImage)
        } else{
            favoriteImagesDao.insertFavoriteImage(favoriteImage)
        }
    }

    override fun getFavoriteImagesIds(): Flow<List<String>> {
        return favoriteImagesDao.getFavoriteImageIds()
    }
}