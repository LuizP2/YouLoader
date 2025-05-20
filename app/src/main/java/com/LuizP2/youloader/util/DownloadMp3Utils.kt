package com.LuizP2.youloader.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File

object DownloadMp3Utils {
    fun getVideoIdFromURL(text: String): String {
        return if (text.startsWith("https://www.youtube.com/watch?")) {
            text.substringAfter("v=", "").substringBefore("&")
        } else text.substringAfter("youtu.be/").substringBefore("?")
    }

    fun downloadMp3(context: Context, fileUrl: String, fileName: String) {
        createDownloadedMusicsFolder()
        val finalDirectory = setMusicFileNameAndPath(fileUrl, fileName)
        downloadFileOnPath(context = context, request = finalDirectory)
    }

    private fun createDownloadedMusicsFolder() {
        val downloadsDiretory = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val targetFolder = File(downloadsDiretory, "Musicas Baixadas")
        if (!targetFolder.exists()) targetFolder.mkdirs()
    }

    private fun setMusicFileNameAndPath(fileUrl: String, fileName: String): DownloadManager.Request? {
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
        return request
    }

    private fun downloadFileOnPath(context: Context, request: DownloadManager.Request?) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}