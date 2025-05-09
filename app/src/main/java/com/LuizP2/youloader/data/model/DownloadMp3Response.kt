package com.LuizP2.youloader.data.model

import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("title")
    val title: String?,
    @SerializedName("link")
    val url: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("format")
    val format: String?
)