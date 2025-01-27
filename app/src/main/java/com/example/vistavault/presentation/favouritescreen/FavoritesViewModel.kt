package com.example.vistavault.presentation.favouritescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vistavault.data.mapper.toDomainModelList
import com.example.vistavault.di.AppModule
import com.example.vistavault.domain.model.UnsplashImage
import com.example.vistavault.domain.repository.ImageRepository
import com.example.vistavault.presentation.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.http.Query
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()


    val favoriteImage: StateFlow<PagingData<UnsplashImage>> = repository.getAllFavoritesImages()
        .catch { exception ->
            _snackbarEvent.send(
                SnackbarEvent(message = "Something went wrong. ${exception.message}")
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope =  viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = PagingData.empty()
        )

    val favoriteImagesIds: StateFlow<List<String>> = repository.getFavoriteImagesIds()
        .catch { exception ->
            _snackbarEvent.send(
                SnackbarEvent(message = "Something went wrong. ${exception.message}")
            )
        }
        .stateIn(
            scope =  viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )
    fun toggleFavoriteStatus(image: UnsplashImage){
        viewModelScope.launch {
            try {
                repository.toggleFavoriteStatus(image)
            } catch (e:Exception) {
                _snackbarEvent.send(
                    SnackbarEvent(message = "Something went wrong. ${e.message}")
                )
            }
        }
    }
}