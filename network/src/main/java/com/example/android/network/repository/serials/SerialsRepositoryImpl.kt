package com.example.android.network.repository.serials

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serial.SerialsList
import com.example.android.network.models.serial.actorserials.ActorSerials

class SerialsRepositoryImpl : SerialsRepository {

    override fun getPopularSerials(callback: SerialsCallback<SerialsList>) {

        HttpRequest.getInstance()?.load(APIClient.GET_POPULAR_SERIALS, object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, SerialsList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getSerialDetails(serialId: Int, callback: SerialsCallback<SerialDetails>) {

        HttpRequest.getInstance()?.load(APIClient.getSerialDetails(serialId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, SerialDetails::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getRecommendedSerialsForSerial(
        serialId: Int,
        callback: SerialsCallback<RecommendSerialsList>
    ) {

        HttpRequest.getInstance()
            ?.load(APIClient.getRecommendedSerialsForSerial(serialId), object : Callback {
                override fun onSuccess(json: String) {
                    callback.onSuccess(
                        Converter.parsingJson(
                            json,
                            RecommendSerialsList::class.java
                        )
                    )
                }

                override fun onError(throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
    }

    override fun getActorSerials(actorId: Int, callback: SerialsCallback<ActorSerials>) {

        HttpRequest.getInstance()?.load(APIClient.getSerialsWithActor(actorId), object : Callback {
            override fun onSuccess(json: String) {
                callback.onSuccess(Converter.parsingJson(json, ActorSerials::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getSearchResultSerials(query: String, callback: SerialsCallback<SerialsList>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getSearchResultForSerials(query), object : Callback {
                override fun onSuccess(json: String) {
                    callback.onSuccess(Converter.parsingJson(json, SerialsList::class.java))
                }

                override fun onError(throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
    }
}