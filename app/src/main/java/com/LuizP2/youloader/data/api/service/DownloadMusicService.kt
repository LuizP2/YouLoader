package com.LuizP2.youloader.data.api.service

import com.LuizP2.youloader.data.model.Music
import retrofit2.http.GET
import retrofit2.http.Query

interface DownloadMusicService {
    @GET("/dl")
    suspend fun DownloadMp3(
        @Query("id") id: String
    ): Music
}