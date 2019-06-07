package com.example.android.network.repository.videos

import com.example.android.network.NetworkCallback
import com.example.android.network.models.movievideo.MovieVideos

interface VideosRepository{

    fun getVideosForMovie(movieId: Int, callback: NetworkCallback<MovieVideos>)
}