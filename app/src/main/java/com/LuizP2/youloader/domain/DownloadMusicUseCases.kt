package com.LuizP2.youloader.domain

import androidx.lifecycle.LiveData
import com.LuizP2.youloader.data.api.repository.DownloadMp3Repository
import com.LuizP2.youloader.data.model.Music
import javax.inject.Inject

sealed class DownloadStatus {
    object InProgress : DownloadStatus()
    data class Success(val uriString: String) : DownloadStatus()
    data class Error(val message: String) : DownloadStatus()
}

// UseCase para download de música
class DownloadMusicUseCase @Inject constructor(
    private val repository: DownloadMp3Repository
) {
    suspend operator fun invoke(url: String): Result<Music> {
        return repository.DownloadMp3(url)
    }
}