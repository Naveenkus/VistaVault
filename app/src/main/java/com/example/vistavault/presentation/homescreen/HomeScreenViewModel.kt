package com.example.vistavault.presentation.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistavault.di.AppModule
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {
    var images : String by mutableStateOf("")

    init {
        getImages()
    }
    private fun getImages() {
        viewModelScope.launch {
            val result = AppModule.retrofitService.getEditorialFeedImages()
            images = result
        }
    }
}