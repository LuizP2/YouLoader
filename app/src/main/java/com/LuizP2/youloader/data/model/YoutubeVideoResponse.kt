package com.LuizP2.youloader.data.model

import com.google.gson.annotations.SerializedName


data class YoutubeVideoResponse(
    @SerializedName("items") val items: List<VideoItem>
)

data class VideoItem(
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val snippet: VideoSnippet
)


data class VideoSnippet(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: VideoImageType,
    @SerializedName("publishedAt") val publishedAt: String,
)

data class VideoImageType(
    @SerializedName("high") val high: Thumbnail
)

data class Thumbnail(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null
)