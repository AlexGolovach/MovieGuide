package com.example.android.database.repository.movie

import android.content.ContentValues
import android.content.Context
import com.example.android.database.BaseSQLiteOpenHelper
import com.example.android.database.DatabaseCallback
import com.example.android.database.IdGenerator
import com.example.android.database.NO_ITEMS
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video

internal class MovieDatabaseRepositoryImpl(context: Context) : BaseSQLiteOpenHelper(context),
    MovieDatabaseRepository {

    override fun addInformation(details: Details) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $DETAILS_TABLE WHERE $ID=?",
                arrayOf(details.id.toString())
            )

        cursor.use {
            if (it.count == 0) {

                val values = ContentValues()

                values.put(ID, details.id)
                values.put(TITLE, details.title)
                values.put(DESCRIPTION, details.description)
                values.put(DATE, details.date)
                values.put(RUNTIME, details.runtime)
                values.put(IMAGE, details.image)

                db.insert(DETAILS_TABLE, null, values)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadInformation(id: Int, databaseCallback: DatabaseCallback<Details>) {
        val cursor =
            db.rawQuery("SELECT * FROM $DETAILS_TABLE WHERE $ID=?", arrayOf(id.toString()))

        cursor.use {
            if (it.moveToFirst()) {
                val primaryId = it.getInt(it.getColumnIndex(ID))
                val title = it.getString(it.getColumnIndex(TITLE))
                val description = it.getString(it.getColumnIndex(DESCRIPTION))
                val date = it.getString(it.getColumnIndex(DATE))
                val runtime = it.getString(it.getColumnIndex(RUNTIME))
                val image = it.getString(it.getColumnIndex(IMAGE))

                databaseCallback.onSuccess(
                    Details(
                        primaryId,
                        title,
                        description,
                        date,
                        runtime,
                        image
                    )
                )
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun addActorSquad(movieId: Int, actorSquad: List<ActorSquad>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $ACTOR_SQUAD_TABLE WHERE $ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until actorSquad.size) {
                    values.put(ACTOR_ID, actorSquad[i].id)
                    values.put(ID, movieId)
                    values.put(ACTOR_NAME, actorSquad[i].actorName)
                    values.put(CHARACTER, actorSquad[i].character)
                    values.put(IMAGE, actorSquad[i].image)

                    db.insert(ACTOR_SQUAD_TABLE, null, values)
                }
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadActorSquad(
        movieId: Int,
        databaseCallback: DatabaseCallback<List<ActorSquad>>
    ) {
        val cursor = db.rawQuery(
            "SELECT * FROM $ACTOR_SQUAD_TABLE WHERE $ID=?",
            arrayOf(movieId.toString())
        )

        val actorSquadList = mutableListOf<ActorSquad>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val actorId = it.getInt(it.getColumnIndex(ACTOR_ID))
                    val id = it.getInt(it.getColumnIndex(ID))
                    val name = it.getString(it.getColumnIndex(ACTOR_NAME))
                    val character = it.getString(it.getColumnIndex(CHARACTER))
                    val image = it.getString(it.getColumnIndex(IMAGE))

                    actorSquadList.add(ActorSquad(actorId, id, name, character, image))
                } while (it.moveToNext())

                databaseCallback.onSuccess(actorSquadList)
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun addRecommendedMovies(movieId: Int, recommendMovie: List<Movie>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $RECOMMENDED_MOVIE_TABLE WHERE $ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until recommendMovie.size) {
                    values.put(ID, IdGenerator.generateId())
                    values.put(RECOMMENDED_ID, recommendMovie[i].id)
                    values.put(MOVIE_OR_SERIAL_ID, movieId)
                    values.put(TITLE, recommendMovie[i].title)
                    values.put(RATING, recommendMovie[i].rating)
                    values.put(IMAGE, recommendMovie[i].image)

                    db.insert(RECOMMENDED_MOVIE_TABLE, null, values)
                }
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadRecommendedMovies(
        movieId: Int,
        databaseCallback: DatabaseCallback<List<Movie>>
    ) {
        val cursor = db.rawQuery(
            "SELECT * FROM $RECOMMENDED_MOVIE_TABLE WHERE $ID=?",
            arrayOf(movieId.toString())
        )

        val recommendMovieList = mutableListOf<Movie>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val movieOrSerialId = it.getInt(it.getColumnIndex(MOVIE_OR_SERIAL_ID))
                    val id = it.getInt(it.getColumnIndex(ID))
                    val title = it.getString(it.getColumnIndex(TITLE))
                    val rating = it.getDouble(it.getColumnIndex(RATING))
                    val image = it.getString(it.getColumnIndex(IMAGE))

                    recommendMovieList.add(Movie(movieOrSerialId, id, title, rating, image))
                } while (it.moveToNext())

                databaseCallback.onSuccess(recommendMovieList)
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun addVideo(movieId: Int, video: List<Video>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $VIDEO_TABLE WHERE $ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until video.size) {
                    values.put(ID, video[i].id)
                    values.put(MOVIE_OR_SERIAL_ID, movieId)
                    values.put(VIDEO_ID, video[i].videoId)
                    values.put(TITLE, video[i].title)

                    db.insert(VIDEO_TABLE, null, values)
                }
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadVideo(movieId: Int, databaseCallback: DatabaseCallback<List<Video>>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $VIDEO_TABLE WHERE $MOVIE_OR_SERIAL_ID=?",
                arrayOf(movieId.toString())
            )

        val videoList = mutableListOf<Video>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(ID))
                    val movieOrSerialId = it.getInt(it.getColumnIndex(MOVIE_OR_SERIAL_ID))
                    val videoId = it.getString(it.getColumnIndex(VIDEO_ID))
                    val title = it.getString(it.getColumnIndex(TITLE))

                    videoList.add(Video(id, movieOrSerialId, videoId, title))
                } while (it.moveToNext())

                databaseCallback.onSuccess(videoList)
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun checkMovieInFavorite(
        userId: Int,
        filmOrSerial: Int,
        databaseCallback: DatabaseCallback<Boolean>
    ) {
        val cursor = db.rawQuery(
            "SELECT * FROM $FAVORITE_TABLE WHERE $USER_ID=? AND $MOVIE_OR_SERIAL_ID=?",
            arrayOf(userId.toString(), filmOrSerial.toString())
        )

        cursor.use {
            if (it.count == 0) {
                databaseCallback.onSuccess(false)
            } else {
                databaseCallback.onSuccess(true)
            }

            BaseSQLiteOpenHelper::close
        }
    }
}