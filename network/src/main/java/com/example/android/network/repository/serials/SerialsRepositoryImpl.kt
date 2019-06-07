package com.example.android.network.repository.serials

import com.example.android.network.APIClient
import com.example.android.network.Converter
import com.example.android.network.NetworkCallback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serial.SerialsList
import com.example.android.network.models.serial.actorserials.ActorSerials

class SerialsRepositoryImpl : SerialsRepository {

    override fun getPopularSerials(callback: NetworkCallback<SerialsList>) {

        HttpRequest.getInstance()?.load(APIClient.GET_POPULAR_SERIALS, object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, SerialsList::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getSerialDetails(serialId: Int, callback: NetworkCallback<SerialDetails>) {

        HttpRequest.getInstance()?.load(APIClient.getSerialDetails(serialId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, SerialDetails::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getRecommendedSerialsForSerial(
        serialId: Int,
        callback: NetworkCallback<RecommendSerialsList>
    ) {

        HttpRequest.getInstance()
            ?.load(APIClient.getRecommendedSerialsForSerial(serialId), object :
                NetworkCallback<String> {
                override fun onSuccess(result: String) {
                    callback.onSuccess(
                        Converter.parsingJson(
                            result,
                            RecommendSerialsList::class.java
                        )
                    )
                }

                override fun onError(throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
    }

    override fun getActorSerials(actorId: Int, callback: NetworkCallback<ActorSerials>) {

        HttpRequest.getInstance()?.load(APIClient.getSerialsWithActor(actorId), object :
            NetworkCallback<String> {
            override fun onSuccess(result: String) {
                callback.onSuccess(Converter.parsingJson(result, ActorSerials::class.java))
            }

            override fun onError(throwable: Throwable) {
                callback.onError(throwable)
            }
        })
    }

    override fun getSearchResultSerials(query: String, callback: NetworkCallback<SerialsList>) {

        HttpRequest.getInstance()
            ?.load(APIClient.getSearchResultForSerials(query), object :
                NetworkCallback<String> {
                override fun onSuccess(result: String) {
                    callback.onSuccess(Converter.parsingJson(result, SerialsList::class.java))
                }

                override fun onError(throwable: Throwable) {
                    callback.onError(throwable)
                }
            })
    }
}