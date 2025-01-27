package com.example.vistavault.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.data.remote.UnsplashApiService
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.util.Constants

class SearchPagingSource(
    private val query: String,
    private val unsplashApi : UnsplashApiService
): PagingSource<Int, UnsplashImage>() {

    companion object {
        private const val STARTNG_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        Log.d(Constants.IV_LOG_TAG, "getRefreshKey: ${state.anchorPosition}")
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPager = params.key ?: STARTNG_PAGE_INDEX
        Log.d(Constants.IV_LOG_TAG, "currentPage: $currentPager")
        return try {
            val response = unsplashApi.searchImages(
                query = query,
                page = currentPager,
                perPage = params.loadSize
            )

            Log.d(Constants.IV_LOG_TAG, "Full API Response: $response")

            val endOfPaginationReached = response.results.isEmpty()

            Log.d(Constants.IV_LOG_TAG, "Load Result response : ${response.results.toDomainModelList()}")
            Log.d(Constants.IV_LOG_TAG, "endOfPaginationReached : $endOfPaginationReached")

            LoadResult.Page(
                data =  response.results.toDomainModelList(),
                prevKey = if (currentPager == STARTNG_PAGE_INDEX) null else currentPager -1,
                nextKey = if (endOfPaginationReached) null else currentPager + 1
            )

        }catch (e: Exception){
            Log.d(Constants.IV_LOG_TAG, "LoadResultError: ${e.message}")
            LoadResult.Error(e)
        }
    }
}