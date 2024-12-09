package com.example.vistavault.presentation.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.data.remote.dto.UnsplashImageDto
import com.example.vistavault.di.AppModule
import com.example.vistavault.domain.UnsplashImage
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    var images : List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    init {
        getImages()
    }
    private fun getImages() {
        viewModelScope.launch {
            val result = AppModule.retrofitService.getEditorialFeedImages()
            images = result.toDomainModelList()
        }
    }
}