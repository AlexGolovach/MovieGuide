package com.example.android.database.repository.reviews

import com.example.android.database.Callback
import com.example.android.database.model.Reviews
import com.google.firebase.firestore.FirebaseFirestore

class ReviewsRepositoryImpl : ReviewsRepository {

    private val myDB = FirebaseFirestore.getInstance()
    private val reviews = myDB.collection("Reviews")
    private val reviewsList: ArrayList<Reviews> = ArrayList()

    override fun addReviewForFilm(
        id: Int,
        userName: String,
        review: String
    ) {
        val documentId = reviews.document().id

        reviews
            .add(
                mapOf(
                    "documentId" to documentId,
                    "id" to id,
                    "userName" to userName,
                    "review" to review
                )
            )
    }

    override fun loadReviewsForFilm(id: Int, callback: Callback<ArrayList<Reviews>>) {
        reviewsList.clear()

        reviews
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                it.forEach { queryDocumentSnapshot ->
                    val documentId = queryDocumentSnapshot.id
                    val tvId = queryDocumentSnapshot.get("id") as Long
                    val userName = queryDocumentSnapshot.get("userName").toString()
                    val review = queryDocumentSnapshot.get("review").toString()

                    reviewsList.add(Reviews(documentId, tvId.toInt(), userName, review))

                    if (reviewsList.size != 0) {
                        callback.onSuccess(reviewsList)
                    } else {
                        callback.onError(NullPointerException("No reviews"))
                    }
                }
            }
    }

    override fun deleteReview(documentId: String) {
        reviews.document(documentId).delete()
    }
}