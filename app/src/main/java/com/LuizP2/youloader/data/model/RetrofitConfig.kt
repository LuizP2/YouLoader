package com.LuizP2.youloader.data.model

import com.LuizP2.youloader.BuildConfig
import com.LuizP2.youloader.data.api.service.DownloadMusicService
import com.LuizP2.youloader.data.api.service.YoutubeDatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideYouTubeService(retrofit: Retrofit): YoutubeDatabaseService =
        retrofit.create(YoutubeDatabaseService::class.java)


    @Provides
    @Singleton
    @Named("DownloadMusicRetrofit")
    fun DownloadProvideRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        BuildConfig.MP3_API_KEY
                    )
                    .addHeader("x-rapidapi-host", "youtube-mp36.p.rapidapi.com")
                    .addHeader("Content-Type", "application/json")
                    .build()
                it.proceed(request)
            }
            .build()


        return Retrofit.Builder()
            .baseUrl("https://youtube-mp36.p.rapidapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideDownloadService(@Named("DownloadMusicRetrofit") retrofit: Retrofit): DownloadMusicService =
        retrofit.create(DownloadMusicService::class.java)
}
