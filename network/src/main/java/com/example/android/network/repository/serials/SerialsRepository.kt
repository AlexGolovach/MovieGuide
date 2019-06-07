package com.example.android.network.repository.serials

import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serial.SerialsList
import com.example.android.network.models.serial.actorserials.ActorSerials

interface SerialsRepository{

    fun getPopularSerials(callback: SerialsCallback<SerialsList>)

    fun getSerialDetails(serialId: Int, callback: SerialsCallback<SerialDetails>)

    fun getRecommendedSerialsForSerial(serialId: Int, callback: SerialsCallback<RecommendSerialsList>)

    fun getActorSerials(actorId: Int, callback: SerialsCallback<ActorSerials>)

    fun getSearchResultSerials(query: String, callback: SerialsCallback<SerialsList>)
}