package com.LuizP2.youloader.data.api.repository

import com.LuizP2.youloader.data.api.service.YoutubeDatabaseService
import com.LuizP2.youloader.data.model.VideoItem
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject

interface YouTubeRepository {
    suspend fun search(query: String): Result<List<VideoItem>>
    suspend fun searchByID(videoId: String): Result<VideoItem>
}

class YouTubeRepositoryImpl @Inject constructor(
    private val api: YoutubeDatabaseService
) : YouTubeRepository {
    override suspend fun search(query: String): Result<List<VideoItem>> {
        return try {
            val response = api.searchVideos(query = query)
            Result.success(response.items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchByID(videoId: String): Result<VideoItem> {
        return try {
            val response = api.searchByID(id = videoId)
            val video = response.items.firstOrNull()
            if (video != null) Result.success(video)
            else Result.failure(Exception("Vídeo não encontrado"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindYouTubeRepository(
        impl: YouTubeRepositoryImpl
    ): YouTubeRepository
}