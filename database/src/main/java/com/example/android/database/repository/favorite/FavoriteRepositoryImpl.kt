package com.example.android.database.repository.favorite

import android.content.ContentValues
import android.content.Context
import com.example.android.database.BaseSQLiteOpenHelper
import com.example.android.database.DatabaseCallback
import com.example.android.database.IdGenerator
import com.example.android.database.model.Details
import com.example.android.database.model.Favorite

internal class FavoriteRepositoryImpl(context: Context) : BaseSQLiteOpenHelper(context), FavoriteRepository {

    override fun addToFavorite(userId: Int, details: Details, type: String) {
        val cursor = db.rawQuery(
            "SELECT * FROM $FAVORITE_TABLE WHERE $USER_ID=? AND $MOVIE_OR_SERIAL_ID=?",
            arrayOf(userId.toString(), details.id.toString())
        )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                values.put(ID, IdGenerator.generateId())
                values.put(USER_ID, userId)
                values.put(MOVIE_OR_SERIAL_ID, details.id)
                values.put(TITLE, details.title)
                values.put(IMAGE, details.image)
                values.put(TYPE, type)

                db.insert(FAVORITE_TABLE, null, values)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadFavorites(userId: Int, callback: DatabaseCallback<ArrayList<Favorite>>) {
        val cursor = db.rawQuery(
            "SELECT * FROM $FAVORITE_TABLE WHERE $USER_ID=?",
            arrayOf(userId.toString())
        )

        val favoritesList = ArrayList<Favorite>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val favoriteId = it.getInt(it.getColumnIndex(ID))
                    val accountId = it.getInt(it.getColumnIndex(USER_ID))
                    val movieOrSerialId = it.getInt(it.getColumnIndex(MOVIE_OR_SERIAL_ID))
                    val title = it.getString(it.getColumnIndex(TITLE))
                    val image = it.getString(it.getColumnIndex(IMAGE))
                    val type = it.getString(it.getColumnIndex(TYPE))

                    favoritesList.add(
                        Favorite(
                            favoriteId,
                            accountId,
                            movieOrSerialId,
                            title,
                            image,
                            type
                        )
                    )
                } while (it.moveToNext())
            }

            callback.onSuccess(favoritesList)

            BaseSQLiteOpenHelper::close
        }
    }

    override fun deleteFromFavorites(userId: Int, movieOrSerialId: Int) {
        db.delete(
            FAVORITE_TABLE,
            "$USER_ID=? AND $MOVIE_OR_SERIAL_ID=?",
            arrayOf(userId.toString(), movieOrSerialId.toString())
        )
    }
}