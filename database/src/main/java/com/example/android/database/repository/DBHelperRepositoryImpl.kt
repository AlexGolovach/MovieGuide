package com.example.android.database.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.database.Callback
import com.example.android.database.model.*

class DBHelperRepositoryImpl(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
), DBHelperRepository {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MOVIE.db"

        private const val USER_TABLE_NAME = "User"
        private const val USER_ID = "Id"
        private const val USER_LOGIN = "Login"
        private const val USER_EMAIL = "Email"
        private const val USER_PASSWORD = "Password"

        private const val TABLE_NAME = "MovieAndSerials"
        private const val ID = "Id"
        private const val TITLE = "Title"
        private const val DESCRIPTION = "Description"
        private const val DATE = "Date"
        private const val RUNTIME = "Runtime"
        private const val IMAGE = "Image"

        private const val VIDEO_TABLE_NAME = "Video"
        private const val MOVIE_ID = "Id"
        private const val VIDEO_ID = "VideoId"

        private const val MOVIE_TABLE_NAME = "RecommendedMovie"
        private const val RECOMMEND_MOVIE_ID = "Id"
        private const val MOVIE_TITLE = "Title"
        private const val RATING = "Rating"
        private const val RECOMMENDED_MOVIE_IMAGE = "Image"

        private const val ACTOR_SQUAD_TABLE_NAME = "ActorSquad"
        private const val ACTOR_ID = "Id"
        private const val ACTOR_NAME = "Name"
        private const val CHARACTER = "Character"
        private const val ACTOR_IMAGE = "Image"
    }

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        //TODO create table if table is not exist
        val userTable =
            ("CREATE TABLE $USER_TABLE_NAME ($USER_ID INTEGER PRIMARY KEY,$USER_LOGIN TEXT,$USER_EMAIL TEXT,$USER_PASSWORD TEXT)")

        val informationTable =
            ("CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY,$TITLE TEXT,$DESCRIPTION TEXT,$DATE TEXT,$RUNTIME TEXT,$IMAGE TEXT)")

        val videoTable =
            ("CREATE TABLE $VIDEO_TABLE_NAME ($MOVIE_ID INTEGER PRIMARY KEY,$VIDEO_ID TEXT)")

        val movieTable =
            ("CREATE TABLE $MOVIE_TABLE_NAME ($RECOMMEND_MOVIE_ID INTEGER PRIMARY KEY,$MOVIE_TITLE TEXT,$RATING DOUBLE,$RECOMMENDED_MOVIE_IMAGE TEXT)")

        val actorSquadTable =
            ("CREATE TABLE $ACTOR_SQUAD_TABLE_NAME ($ACTOR_ID INTEGER PRIMARY KEY,$ACTOR_NAME TEXT,$CHARACTER TEXT,$ACTOR_IMAGE TEXT)")

        db?.execSQL(userTable)
        db?.execSQL(informationTable)
        db?.execSQL(videoTable)
        db?.execSQL(movieTable)
        db?.execSQL(actorSquadTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        onCreate(db)
    }

    override fun addUser(user: User, callback: Callback<User>) {

        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_LOGIN=? OR $USER_EMAIL=?",
            arrayOf(user.login, user.email)
        )
        cursor.use {
            if (cursor.count == 0) {

                val values = ContentValues()

                values.put(USER_ID, user.id)
                values.put(USER_LOGIN, user.login)
                values.put(USER_EMAIL, user.email)
                values.put(USER_PASSWORD, user.password)

                val result = db.insert(USER_TABLE_NAME, null, values)

                if (result == (-1).toLong()) {
                    callback.onError("Error")
                } else {
                    callback.onSuccess(user)
                }
            } else {
                callback.onError("Login or email exist. Please write new information")
            }

            SQLiteOpenHelper::close
        }
    }

    override fun findUser(email: String, password: String, callback: Callback<User>) {
        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_EMAIL=? AND $USER_PASSWORD=?",
            arrayOf(email, password)
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val userId = it.getInt(it.getColumnIndex(USER_ID))
                    val userLogin = it.getString(it.getColumnIndex(USER_LOGIN))
                    val userEmail = it.getString(it.getColumnIndex(USER_EMAIL))
                    val userPassword = it.getString(it.getColumnIndex(USER_PASSWORD))

                    callback.onSuccess(User(userId, userLogin, userEmail, userPassword))
                } while (it.moveToNext())
            } else {
                callback.onError("Email or password not correct")
            }
        }
    }

    override fun updateUser(user: User, callback: Callback<Int>) {
        val values = ContentValues()

        values.put(USER_ID, user.id)
        values.put(USER_LOGIN, user.login)
        values.put(USER_EMAIL, user.email)
        values.put(USER_PASSWORD, user.password)

        callback.onSuccess(
            db.update(
                USER_TABLE_NAME,
                values,
                "$USER_ID=?",
                arrayOf(user.id.toString())
            )
        )
    }

    override fun deleteUser(user: User) {
        db.delete(USER_TABLE_NAME, "$USER_ID=?", arrayOf(user.id.toString()))
        db.close()
    }

    override fun addInformation(information: Information) {
        val cursor =
            db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID=?", arrayOf(information.id.toString()))

        cursor.use {
            if (it.count == 0) {

                val values = ContentValues()

                values.put(ID, information.id)
                values.put(TITLE, information.title)
                values.put(DESCRIPTION, information.description)
                values.put(DATE, information.date)
                values.put(RUNTIME, information.runtime)
                values.put(IMAGE, information.image)

                db.insert(TABLE_NAME, null, values)
            }

            SQLiteOpenHelper::close
        }
    }

    override fun loadInformation(id: Int, callback: Callback<Information>) {
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID=?", arrayOf(id.toString()))

        cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex(ID))
                val title = it.getString(it.getColumnIndex(TITLE))
                val description = it.getString(it.getColumnIndex(DESCRIPTION))
                val date = it.getString(it.getColumnIndex(DATE))
                val runtime = it.getString(it.getColumnIndex(RUNTIME))
                val image = it.getString(it.getColumnIndex(IMAGE))

                callback.onSuccess(Information(id, title, description, date, runtime, image))
            } else {
                callback.onError("No items")
            }
        }
    }

    override fun addActorSquad(movieId: Int, actorSquad: List<ActorSquad>) {
        val cursor =
            db.rawQuery(
                "SELECT * FROM $ACTOR_SQUAD_TABLE_NAME WHERE $ACTOR_ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until actorSquad.size) {
                    values.put(ACTOR_ID, movieId)
                    values.put(ACTOR_NAME, actorSquad[i].actorName)
                    values.put(CHARACTER, actorSquad[i].character)
                    values.put(ACTOR_IMAGE, actorSquad[i].image)

                    db.insert(ACTOR_SQUAD_TABLE_NAME, null, values)
                }
            }

            db.close()
        }
    }

    override fun loadActorSquad(movieId: Int, callback: Callback<List<ActorSquad>>){
        val cursor = db.rawQuery("SELECT * FROM $ACTOR_SQUAD_TABLE_NAME WHERE $ACTOR_ID=?", arrayOf(movieId.toString()))

        val actorSquadList = mutableListOf<ActorSquad>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(ACTOR_ID))
                    val name = it.getString(it.getColumnIndex(ACTOR_NAME))
                    val character = it.getString(it.getColumnIndex(CHARACTER))
                    val image = it.getString(it.getColumnIndex(ACTOR_IMAGE))

                    actorSquadList.add(ActorSquad(id,name,character, image))
                } while (it.moveToNext())

                callback.onSuccess(actorSquadList)
            } else {
                callback.onError("No items")
            }
        }
    }

    override fun addRecommendedMovies(movieId: Int, recommendMovie: List<Movie>){
        val cursor =
            db.rawQuery(
                "SELECT * FROM $MOVIE_TABLE_NAME WHERE $RECOMMEND_MOVIE_ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until recommendMovie.size) {
                    values.put(RECOMMEND_MOVIE_ID, movieId)
                    values.put(MOVIE_TITLE, recommendMovie[i].title)
                    values.put(RATING, recommendMovie[i].rating)
                    values.put(RECOMMENDED_MOVIE_IMAGE, recommendMovie[i].image)

                    db.insert(MOVIE_TABLE_NAME, null, values)
                }
            }

            db.close()
        }
    }

    override fun loadRecommendedMovies(movieId: Int, callback: Callback<List<Movie>>){
        val cursor = db.rawQuery("SELECT * FROM $MOVIE_TABLE_NAME WHERE $RECOMMEND_MOVIE_ID=?", arrayOf(movieId.toString()))

        val recommendMovieList = mutableListOf<Movie>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(RECOMMEND_MOVIE_ID))
                    val title = it.getString(it.getColumnIndex(MOVIE_TITLE))
                    val rating = it.getDouble(it.getColumnIndex(RATING))
                    val image = it.getString(it.getColumnIndex(RECOMMENDED_MOVIE_IMAGE))

                    recommendMovieList.add(Movie(id,title,rating, image))
                } while (it.moveToNext())

                callback.onSuccess(recommendMovieList)
            } else {
                callback.onError("No items")
            }
        }
    }

    override fun addVideo(movieId: Int, video: List<String>){
        val cursor =
            db.rawQuery(
                "SELECT * FROM $VIDEO_TABLE_NAME WHERE $MOVIE_ID=?",
                arrayOf(movieId.toString())
            )

        cursor.use {
            if (it.count == 0) {
                val values = ContentValues()

                for (i in 0 until video.size) {
                    values.put(MOVIE_ID, movieId)
                    values.put(VIDEO_ID, video[i])

                    db.insert(VIDEO_TABLE_NAME, null, values)
                }
            }

            SQLiteOpenHelper::close
        }
    }

    override fun loadVideo(movieId: Int, callback: Callback<List<String>>){
        val cursor = db.rawQuery("SELECT * FROM $VIDEO_TABLE_NAME WHERE $MOVIE_ID=?", arrayOf(movieId.toString()))

        val videoList = mutableListOf<String>()

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val videoId = it.getString(it.getColumnIndex(VIDEO_ID))

                    videoList.add(videoId)
                } while (it.moveToNext())

                callback.onSuccess(videoList)
            } else {
                callback.onError("No items")
            }
        }
    }
}