package com.example.android.database.repository.favoriteshows

import com.example.android.database.Callback
import com.example.android.database.model.FavoriteShows
import com.example.android.network.models.showdetails.ShowDetails

interface FavoriteShowsRepository {

    fun addShowInFavorites(userId: String, show: ShowDetails)

    fun downloadMyFavoriteShow(userId: String, callback: Callback<ArrayList<FavoriteShows>>)

    fun deleteShow(show: FavoriteShows, callback: Callback<String>)

    fun checkShowInFavorites(showId: Int, callback: Callback<Boolean>)
}