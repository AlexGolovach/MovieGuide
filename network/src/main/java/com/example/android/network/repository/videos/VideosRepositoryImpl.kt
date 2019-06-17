package com.example.android.network.repository.videos

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.NetworkCallback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.VideoList

internal class VideosRepositoryImpl : VideosRepository {

    override fun getVideosForMovie(movieId: Int, callback: NetworkCallback<VideoList>) {

        HttpRequest.getInstance()?.load(APIClient.getVideoForMovie(movieId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, VideoList::class.java))
            }

            override fun onError(error: String) {
                callback.onError(error)
            }
        })
    }

    override fun getVideosForSerial(serialId: Int, callback: NetworkCallback<VideoList>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getVideoForSerial(serialId), object : NetworkCallback<String> {
                override fun onSuccess(result: String) {
                    callback.onSuccess(Converter.parsingJson(result, VideoList::class.java))
                }

                override fun onError(error: String) {
                    callback.onError(error)
                }
            })
    }
}