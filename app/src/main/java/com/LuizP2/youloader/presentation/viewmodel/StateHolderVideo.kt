package com.LuizP2.youloader.presentation.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LuizP2.youloader.data.model.VideoItem
import com.LuizP2.youloader.domain.SearchVideosUseCases
import com.LuizP2.youloader.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCases
) : ViewModel() {

    var videos by mutableStateOf<Resource<List<VideoItem>>>(Resource.Loading)
        private set

    fun search(query: String) {
        viewModelScope.launch {
            videos = Resource.Loading
            videos = try {
                val result = searchVideosUseCase(query)
                result.fold(
                    onSuccess = { Resource.Success(it) },
                    onFailure = { Resource.Error(it) }
                )
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }
}

@HiltViewModel
class SearchViewByIdViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCases
) : ViewModel() {

    var video by mutableStateOf<Resource<VideoItem>>(Resource.Loading)
        private set

    fun searchById(id: String) {
        viewModelScope.launch {
            video = Resource.Loading
            video = try {
                val result = searchVideosUseCase(id)
                result.fold(
                    onSuccess = { item ->
                        Resource.Success(
                            item.firstOrNull() ?: throw Exception("Video not found")
                        )
                    },
                    onFailure = { Resource.Error(it) }
                )
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }
}