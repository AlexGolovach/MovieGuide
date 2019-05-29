package com.example.android.network.repository.videos

import com.example.android.network.models.video.VideosList

interface VideosCallback {

    fun onSuccess(result: VideosList)

    fun onError(error: Throwable)
}