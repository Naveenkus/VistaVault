package com.example.vistavault.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vistavault.data.mapper.toDomainModel
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.data.paging.SearchPagingSource
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow


class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
): ImageRepository {
    override suspend fun getEditorialFeedImages(): List<UnsplashImage> {
        return unsplashApi.getEditorialFeedImages().toDomainModelList()
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
}