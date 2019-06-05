package com.example.android.database.repository.fullserialsfilms

import com.example.android.database.Callback
import com.example.android.database.model.Film
import com.example.android.database.model.Serial
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

class FullSerialsFilmsRepositoryImpl : FullSerialsFilmsRepository {

    private val myDB = FirebaseFirestore.getInstance()
    private val fullFilms = myDB.collection("Films")
    private val fullSerials = myDB.collection("Serials")

    private var filmsList = mutableListOf<Film>()
    private var serialsList = mutableListOf<Serial>()

    override fun loadFilms(callback: Callback<List<Film>>) {
        filmsList.clear()

        fullFilms
            .get()
            .addOnSuccessListener {
                it.forEach { queryDataSnapshot ->
                    val documentId = queryDataSnapshot.id
                    val filmTitle = queryDataSnapshot.get("filmTitle").toString()
                    val videoId = queryDataSnapshot.get("videoId").toString()

                    filmsList.add(Film(documentId, filmTitle, videoId))

                    if (filmsList.size != 0){
                        filmsList.sortBy {film ->
                            film.filmTitle
                        }

                        callback.onSuccess(filmsList)
                    } else {
                        callback.onError(NullPointerException("We not found films"))
                    }
                }
            }
    }

    override fun loadSerials(callback: Callback<List<Serial>>) {
        serialsList.clear()

        fullSerials
            .get()
            .addOnSuccessListener {
                it.forEach { queryDataSnapshot ->
                    val documentId = queryDataSnapshot.id
                    val serialTitle = queryDataSnapshot.get("serialTitle").toString()
                    val videoId = queryDataSnapshot.get("videoId").toString()

                    serialsList.add(Serial(documentId, serialTitle, videoId))

                    if (serialsList.size != 0){
                        serialsList.sortBy {serial ->
                            serial.serialTitle
                        }

                        callback.onSuccess(serialsList)
                    } else {
                        callback.onError(NullPointerException("We not found serials"))
                    }
                }
            }
    }
}