package com.example.android.network.repository.videos

import com.example.android.network.models.movievideo.MovieVideos

interface VideosCallback {

    fun onSuccess(result: MovieVideos)

    fun onError(error: Throwable)
}