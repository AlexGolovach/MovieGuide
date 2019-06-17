package com.example.android.database.repository.favorite

import com.example.android.database.DatabaseCallback
import com.example.android.database.model.Details
import com.example.android.database.model.Favorite

interface FavoriteRepository {

    fun addToFavorite(userId: Int, details: Details, type: String)

    fun loadFavorites(userId: Int, callback: DatabaseCallback<ArrayList<Favorite>>)

    fun deleteFromFavorites(userId: Int, movieOrSerialId: Int)
}