package com.example.android.network.repository.videos

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.movievideo.MovieVideos

class VideosRepositoryImpl : VideosRepository {

    override fun getVideosForMovie(movieId: Int, callback: VideosCallback) {

        HttpRequest.getInstance()?.load(APIClient.getVideoForMovie(movieId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, MovieVideos::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

}