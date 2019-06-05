package com.example.android.database.repository.fullserialsfilms

import com.example.android.database.Callback
import com.example.android.database.model.Film
import com.example.android.database.model.Serial

interface FullSerialsFilmsRepository {

    fun loadFilms(callback: Callback<List<Film>>)

    fun loadSerials(callback: Callback<List<Serial>>)
}