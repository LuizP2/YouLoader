package com.LuizP2.youloader.data.api.service

import com.LuizP2.youloader.data.model.YoutubeVideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeDatabaseService {
    @GET("/youtube/v3/search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("key") apiKey: String = "AIzaSyAayVaOJ89DVjrjlXBJYWZfc6ZWAzgIjQg", // TODO(MOVE API KEY TO LOCAL.PROPERTIES)
        @Query("maxResults") maxResults: Int = 10
    ): YoutubeVideoResponse

    @GET("/youtube/v3/videos")
    suspend fun searchByID(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("id") id: String,
        @Query("key") apiKey: String = "AIzaSyAayVaOJ89DVjrjlXBJYWZfc6ZWAzgIjQg", // TODO(MOVE API KEY TO LOCAL.PROPERTIES)
        @Query("maxResults") maxResults: Int = 1
    ): YoutubeVideoResponse
}