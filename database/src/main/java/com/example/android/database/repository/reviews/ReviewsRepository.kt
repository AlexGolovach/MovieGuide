package com.example.android.database.repository.reviews

import com.example.android.database.Callback
import com.example.android.database.model.Reviews

interface ReviewsRepository {

    fun addReviewForFilm(id: Int, userName: String, review: String)

    fun loadReviewsForFilm(id: Int, callback: Callback<ArrayList<Reviews>>)

    fun deleteReview(documentId: String)
}