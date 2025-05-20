package com.LuizP2.youloader.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LuizP2.youloader.data.model.Music
import com.LuizP2.youloader.domain.DownloadMusicUseCase
import com.LuizP2.youloader.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadMusicUseCase: DownloadMusicUseCase
) : ViewModel() {
    var music by mutableStateOf<Resource<Music>>(Resource.empty)
        private set

    fun download(id: String) {
        viewModelScope.launch {
            music = Resource.Loading
            music = try {
                val result = downloadMusicUseCase(id)
                result.fold(
                    onSuccess = { Resource.Success(it) },
                    onFailure = { Resource.Error(it) }
                )
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

    fun finishDownloadProcess() {
        music = Resource.DownloadFinished
    }
}

