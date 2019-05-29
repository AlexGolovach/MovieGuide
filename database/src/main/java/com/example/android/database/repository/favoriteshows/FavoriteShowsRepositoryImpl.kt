package com.example.android.database.repository.favoriteshows

import com.example.android.database.Callback
import com.example.android.database.model.FavoriteShows
import com.example.android.network.models.showdetails.ShowDetails
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

class FavoriteShowsRepositoryImpl : FavoriteShowsRepository {

    private val myDB = FirebaseFirestore.getInstance()
    private val favorites = myDB.collection("FavoriteShows")

    private val favoriteShowsList: ArrayList<FavoriteShows> = ArrayList()

    override fun addShowInFavorites(userId: String, show: ShowDetails) {
        val documentId = favorites.document().id

        favorites
            .whereEqualTo("showName", show.name)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    favorites.add(
                        mapOf(
                            "documentId" to documentId,
                            "showId" to show.id,
                            "userId" to userId,
                            "showTitle" to show.name,
                            "showPoster" to show.poster,
                            "rating" to show.rating
                        )
                    )
                }
            }
    }

    override fun downloadMyFavoriteShow(
        userId: String,
        callback: Callback<ArrayList<FavoriteShows>>
    ) {
        favoriteShowsList.clear()

        favorites
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach { queryDocumentSnapshot ->
                        val documentId = queryDocumentSnapshot.id
                        val showId = queryDocumentSnapshot.get("showId") as Long
                        val accountId = queryDocumentSnapshot.get("userId").toString()
                        val show = queryDocumentSnapshot.get("showTitle").toString()
                        val poster = queryDocumentSnapshot.get("showPoster").toString()
                        val rating = queryDocumentSnapshot.get("rating") as Double

                        favoriteShowsList.add(
                            FavoriteShows(
                                documentId,
                                showId.toInt(),
                                accountId,
                                show,
                                poster,
                                rating
                            )
                        )
                        favoriteShowsList.sortBy { item ->
                            item.rating
                        }

                        callback.onSuccess(favoriteShowsList)
                    }
                } else {
                    callback.onError(NullPointerException("No items"))
                }
            }
    }

    override fun deleteShow(show: FavoriteShows, callback: Callback<String>) {
        favorites.document(show.documentId).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                callback.onSuccess("Successfully deleted")
            } else {
                callback.onError(Exception("Error"))
            }
        }
    }

    override fun checkShowInFavorites(showId: Int, callback: Callback<Boolean>) {
        favorites
            .whereEqualTo("showId", showId)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    callback.onSuccess(false)
                } else {
                    callback.onSuccess(true)
                }
            }
    }

}