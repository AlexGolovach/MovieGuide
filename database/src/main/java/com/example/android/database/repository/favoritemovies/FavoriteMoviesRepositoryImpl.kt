package com.example.android.database.repository.favoritemovies

import com.example.android.database.Callback
import com.example.android.database.model.FavoriteMovies
import com.example.android.network.models.moviedetails.MovieDetails
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import java.lang.NullPointerException

class FavoriteMoviesRepositoryImpl : FavoriteMoviesRepository {

    private val myDB = FirebaseFirestore.getInstance()
    private val favorites = myDB.collection("FavoriteMovies")

    private val favoriteMoviesList: ArrayList<FavoriteMovies> = ArrayList()

    override fun addMovieInFavorites(userId: String, movie: MovieDetails) {
        val documentId = favorites.document().id

        favorites
            .whereEqualTo("movieName", movie.title)
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    favorites.add(
                        mapOf(
                            "documentId" to documentId,
                            "movieId" to movie.id,
                            "userId" to userId,
                            "movieTitle" to movie.title,
                            "moviePoster" to movie.poster,
                            "rating" to movie.rating
                        )
                    )
                }
            }
    }

    override fun downloadMyFavoriteMovies(userId: String, callback: Callback<ArrayList<FavoriteMovies>>) {
        favoriteMoviesList.clear()

        favorites
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach { queryDocumentSnapshot ->
                        val documentId = queryDocumentSnapshot.id
                        val movieId = queryDocumentSnapshot.get("movieId") as Long
                        val accountId = queryDocumentSnapshot.get("userId").toString()
                        val movie = queryDocumentSnapshot.get("movieTitle").toString()
                        val poster = queryDocumentSnapshot.get("moviePoster").toString()
                        val rating = queryDocumentSnapshot.get("rating") as Double

                        favoriteMoviesList.add(
                            FavoriteMovies(
                                documentId,
                                movieId.toInt(),
                                accountId,
                                movie,
                                poster,
                                rating
                            )
                        )
                        favoriteMoviesList.sortBy { item ->
                            item.rating
                        }

                        callback.onSuccess(favoriteMoviesList)
                    }
                } else {
                    callback.onError(NullPointerException("No items"))
                }
            }
    }

    override fun checkMovieInFavorites(movieId: Int, callback: Callback<Boolean>) {
        favorites
            .whereEqualTo("movieId", movieId)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    callback.onSuccess(false)
                } else {
                    callback.onSuccess(true)
                }
            }
    }

    override fun deleteMovie(movie: FavoriteMovies, callback: Callback<String>) {
        favorites.document(movie.documentId).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                callback.onSuccess("Successfully deleted")
            } else {
                callback.onError(Exception("Error"))
            }
        }
    }
}