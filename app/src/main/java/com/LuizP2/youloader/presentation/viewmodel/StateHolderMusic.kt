package com.LuizP2.youloader.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LuizP2.youloader.data.model.Music
import com.LuizP2.youloader.domain.DownloadMusicUseCase
import com.LuizP2.youloader.domain.DownloadStatus
import com.LuizP2.youloader.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val DownloadMusicUseCase: DownloadMusicUseCase
) : ViewModel() {
    var music by mutableStateOf<Resource<Music>>(Resource.Loading)
        private set

    fun Download(id: String, title : String) {
        viewModelScope.launch {
            music = Resource.Loading
            music = try{
                val result = DownloadMusicUseCase(id)
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