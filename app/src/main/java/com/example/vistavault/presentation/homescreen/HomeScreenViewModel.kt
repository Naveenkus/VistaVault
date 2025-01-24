package com.example.vistavault.presentation.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.di.AppModule
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.presentation.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()
    var images : List<UnsplashImage> by mutableStateOf(emptyList())
        private set

    init {
        getImages()
    }
    private fun getImages() {
        viewModelScope.launch {
            try {
                val result = repository.getEditorialFeedImages()
                images = result
            } catch (e: UnknownHostException){
                _snackbarEvent.send(
                    SnackbarEvent(message = "No Internet Connection. Please check your network")
                )
            }catch (e: Exception){
                _snackbarEvent.send(
                    SnackbarEvent(message = "Something went wrong: ${e.message}")
                )
            }
        }
    }
}