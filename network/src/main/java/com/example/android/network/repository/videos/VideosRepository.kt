package com.example.android.network.repository.videos

interface VideosRepository{

    fun getVideosForMovie(movieId: Int, callback: VideosCallback)
}