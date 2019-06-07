package com.example.android.network.repository.videos

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.NetworkCallback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.movievideo.MovieVideos

class VideosRepositoryImpl : VideosRepository {

    override fun getVideosForMovie(movieId: Int, callback: NetworkCallback<MovieVideos>) {

        HttpRequest.getInstance()?.load(APIClient.getVideoForMovie(movieId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, MovieVideos::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

}