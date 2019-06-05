package com.example.android.network.repository.videos

import com.example.android.network.Constants
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.video.VideosList

class VideosRepositoryImpl : VideosRepository {

    private lateinit var url: String

    override fun getVideosForMovie(movieId: Int, callback: VideosCallback) {

        url =
            "https://api.themoviedb.org/3/movie/$movieId/videos?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, VideosList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getVideosForShow(showId: Int, callback: VideosCallback) {

        url =
            "https://api.themoviedb.org/3/tv/$showId/videos?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, VideosList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }
}