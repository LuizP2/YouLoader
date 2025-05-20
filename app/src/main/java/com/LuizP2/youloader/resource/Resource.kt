package com.LuizP2.youloader.resource

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    object empty : Resource<Nothing>()
    object DownloadFinished : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
}