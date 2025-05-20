package com.LuizP2.youloader.data.api.repository

import com.LuizP2.youloader.data.api.service.DownloadMusicService
import com.LuizP2.youloader.data.model.Music
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Inject
import javax.inject.Named

fun interface DownloadMp3Repository {
    @Named("DownloadMusic")
    suspend fun DownloadMp3Call(videoId: String): Result<Music>
}

class DownloadRepositoryImpl @Inject constructor(
    private val api: DownloadMusicService
) : DownloadMp3Repository {

    override suspend fun DownloadMp3Call(videoId: String): Result<Music> {
        return try {
            val response = api.DownloadMp3(id = videoId)
            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadRepositoryModule {
    @Binds
    abstract fun bindDownloadRepository(
        impl: DownloadRepositoryImpl
    ): DownloadMp3Repository
}

