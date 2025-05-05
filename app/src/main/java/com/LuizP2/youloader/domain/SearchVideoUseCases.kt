package com.LuizP2.youloader.domain

import com.LuizP2.youloader.data.api.repository.YouTubeRepository
import com.LuizP2.youloader.data.model.VideoItem
import jakarta.inject.Inject

class SearchVideosUseCases @Inject constructor(
    private val repository: YouTubeRepository
) {
    suspend operator fun invoke(query: String): Result<List<VideoItem>> {
        return repository.search(query)
    }
}

class GetVideoByIdUseCase @Inject constructor(
    private val repository: YouTubeRepository
) {
    suspend operator fun invoke(videoId: String): Result<VideoItem> {
        return repository.searchByID(videoId)
    }
}