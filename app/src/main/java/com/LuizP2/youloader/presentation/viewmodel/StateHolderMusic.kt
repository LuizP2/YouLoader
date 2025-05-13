package com.LuizP2.youloader.presentation.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
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
import java.io.File

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val DownloadMusicUseCase: DownloadMusicUseCase
) : ViewModel() {
    var music by mutableStateOf<Resource<Music>>(Resource.Loading)
        private set

    fun Download(id: String, title: String) {
        viewModelScope.launch {
            music = Resource.Loading
            music = try {
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

    fun FinishDownloadProcess() {
        music = Resource.DownloadFinished
    }

}


fun downloadMp3(
    context: Context,
    fileUrl: String,
    fileName: String
) {
    val downloadsDiretory = Environment
        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val targetFolder = File(downloadsDiretory, "Musicas Baixadas")
    if (!targetFolder.exists()) targetFolder.mkdirs()

    val destinationFile = File(targetFolder, fileName)
    val request = DownloadManager.Request(Uri.parse(fileUrl))
        .setTitle(fileName)
        .setDescription("Baixando música…")
        .setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        )
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "Musicas Baixadas/$fileName"
        )
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(false)


    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE)
            as DownloadManager
    downloadManager.enqueue(request)
}
