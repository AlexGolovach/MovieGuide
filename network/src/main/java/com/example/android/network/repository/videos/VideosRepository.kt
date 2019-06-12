package com.example.android.network.repository.videos

import com.example.android.network.NetworkCallback
import com.example.android.network.models.VideoList

interface VideosRepository {

    fun getVideosForMovie(movieId: Int, callback: NetworkCallback<VideoList>)

    fun getVideosForSerial(serialId: Int, callback: NetworkCallback<VideoList>)
}