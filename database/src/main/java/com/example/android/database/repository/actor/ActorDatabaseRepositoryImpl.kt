package com.example.android.database.repository.actor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.database.BaseSQLiteOpenHelper
import com.example.android.database.DatabaseCallback
import com.example.android.database.IdGenerator
import com.example.android.database.NO_ITEMS
import com.example.android.database.model.Actor
import com.example.android.database.model.Movie

internal class ActorDatabaseRepositoryImpl(context: Context) : BaseSQLiteOpenHelper(context),
    ActorDatabaseRepository {

    override fun addActorInformation(actor: Actor) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $ACTOR_TABLE WHERE $ID=?",
                arrayOf(actor.id.toString())
            )

        cursor.use {
            if (it.count == 0) {

                val values = ContentValues()

                values.put(ID, actor.id)
                values.put(ACTOR_NAME, actor.actorName)
                values.put(BIOGRAPHY, actor.biography)
                values.put(IMAGE, actor.image)

                db.insert(ACTOR_TABLE, null, values)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadActorInformation(id: Int, databaseCallback: DatabaseCallback<Actor>) {
        val cursor =
            db.rawQuery("SELECT * FROM $ACTOR_TABLE WHERE $ID=?", arrayOf(id.toString()))

        cursor.use {
            if (it.moveToFirst()) {
                val primaryId = it.getInt(it.getColumnIndex(ID))
                val actorName = it.getString(it.getColumnIndex(ACTOR_NAME))
                val biography = it.getString(it.getColumnIndex(BIOGRAPHY))
                val image = it.getString(it.getColumnIndex(IMAGE))

                databaseCallback.onSuccess(Actor(primaryId, actorName, biography, image))
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun addActorImages(actorId: Int, image: List<String>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $IMAGES_TABLE WHERE $ID=?",
                arrayOf(actorId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until image.size) {
                    values.put(ID, IdGenerator.generateId())
                    values.put(ACTOR_ID, actorId)
                    values.put(IMAGE, image[i])

                    db.insert(IMAGES_TABLE, null, values)
                }
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadActorImages(id: Int, databaseCallback: DatabaseCallback<List<String>>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $IMAGES_TABLE WHERE $ACTOR_ID=?",
                arrayOf(id.toString())
            )

        val movieList = mutableListOf<String>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val image = it.getString(it.getColumnIndex(IMAGE))

                    movieList.add(image)
                } while (it.moveToNext())

                databaseCallback.onSuccess(movieList)
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun addActorMovies(actorId: Int, movie: List<Movie>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $MOVIE_WITH_ACTOR_TABLE WHERE $ID=?",
                arrayOf(actorId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until movie.size) {
                    values.put(ID, IdGenerator.generateId())
                    values.put(MOVIE_OR_SERIAL_ID, movie[i].movieOrSerialId)
                    values.put(ACTOR_ID, actorId)
                    values.put(TITLE, movie[i].title)
                    values.put(RATING, movie[i].rating)
                    values.put(IMAGE, movie[i].image)

                    db.insert(MOVIE_WITH_ACTOR_TABLE, null, values)
                }
            }

            BaseSQLiteOpenHelper::close
        }
    }

    override fun loadActorMovies(id: Int, databaseCallback: DatabaseCallback<List<Movie>>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $MOVIE_WITH_ACTOR_TABLE WHERE $ID=?",
                arrayOf(id.toString())
            )

        val movieList = mutableListOf<Movie>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(ID))
                    val movieOrSerialId = it.getInt(it.getColumnIndex(MOVIE_OR_SERIAL_ID))
                    val title = it.getString(it.getColumnIndex(TITLE))
                    val rating = it.getDouble(it.getColumnIndex(RATING))
                    val image = it.getString(it.getColumnIndex(IMAGE))

                    movieList.add(Movie(movieOrSerialId, id, title, rating, image))
                } while (it.moveToNext())

                databaseCallback.onSuccess(movieList)
            } else {
                databaseCallback.onError(NO_ITEMS)
            }

            BaseSQLiteOpenHelper::close
        }
    }
}