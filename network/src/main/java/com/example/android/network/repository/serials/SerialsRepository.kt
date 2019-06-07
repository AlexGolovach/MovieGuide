package com.example.android.network.repository.serials

import com.example.android.network.NetworkCallback
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serial.SerialsList
import com.example.android.network.models.serial.actorserials.ActorSerials

interface SerialsRepository{

    fun getPopularSerials(callback: NetworkCallback<SerialsList>)

    fun getSerialDetails(serialId: Int, callback: NetworkCallback<SerialDetails>)

    fun getRecommendedSerialsForSerial(serialId: Int, callback: NetworkCallback<RecommendSerialsList>)

    fun getActorSerials(actorId: Int, callback: NetworkCallback<ActorSerials>)

    fun getSearchResultSerials(query: String, callback: NetworkCallback<SerialsList>)
}