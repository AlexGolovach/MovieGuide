package com.example.android.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

abstract class BaseSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MOVIE.db"

        const val USER_TABLE = "User"
        const val DETAILS_TABLE = "MovieAndSerials"
        const val VIDEO_TABLE = "Video"
        const val RECOMMENDED_MOVIE_TABLE = "RecommendedMovie"
        const val ACTOR_SQUAD_TABLE = "ActorSquad"
        const val ACTOR_TABLE = "Actor"
        const val IMAGES_TABLE = "ActorImage"
        const val MOVIE_WITH_ACTOR_TABLE = "Movie"
        const val FAVORITE_TABLE = "Favorites"

        const val RECOMMENDED_ID = "RecommendedMovieId"
        const val ID = "Id"
        const val MOVIE_OR_SERIAL_ID = "MovieOrSerialId"
        const val ACTOR_ID = "ActorId"
        const val USER_ID = "UserId"
        const val VIDEO_ID = "VideoId"

        const val LOGIN = "Login"
        const val EMAIL = "Email"
        const val PASSWORD = "Password"
        const val TITLE = "Title"
        const val DESCRIPTION = "Description"
        const val DATE = "Date"
        const val RUNTIME = "Runtime"
        const val IMAGE = "Image"
        const val RATING = "Rating"
        const val ACTOR_NAME = "Name"
        const val CHARACTER = "Character"
        const val BIOGRAPHY = "Biography"
        const val TYPE = "Type"
    }

    val db: SQLiteDatabase = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {

        val userTable =
            ("CREATE TABLE IF NOT EXISTS $USER_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$LOGIN TEXT,$EMAIL TEXT," +
                    "$PASSWORD TEXT)")

        val informationTable =
            ("CREATE TABLE IF NOT EXISTS $DETAILS_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$TITLE TEXT," +
                    "$DESCRIPTION TEXT," +
                    "$DATE TEXT," +
                    "$RUNTIME TEXT," +
                    "$IMAGE TEXT)")

        val videoTable =
            ("CREATE TABLE IF NOT EXISTS $VIDEO_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$MOVIE_OR_SERIAL_ID INTEGER," +
                    "$VIDEO_ID TEXT," +
                    "$TITLE TEXT)")

        val movieTable =
            ("CREATE TABLE IF NOT EXISTS $RECOMMENDED_MOVIE_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$RECOMMENDED_ID INTEGER," +
                    "$MOVIE_OR_SERIAL_ID INTEGER," +
                    "$TITLE TEXT," +
                    "$RATING DOUBLE," +
                    "$IMAGE TEXT)")

        val actorSquadTable =
            ("CREATE TABLE IF NOT EXISTS $ACTOR_SQUAD_TABLE (" +
                    "$ACTOR_ID INTEGER PRIMARY KEY," +
                    "$ID INTEGER," +
                    "$ACTOR_NAME TEXT," +
                    "$CHARACTER TEXT," +
                    "$IMAGE TEXT)")

        val actorTable =
            ("CREATE TABLE IF NOT EXISTS $ACTOR_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$ACTOR_NAME TEXT," +
                    "$BIOGRAPHY TEXT," +
                    "$IMAGE TEXT)")

        val actorImageTable =
            ("CREATE TABLE IF NOT EXISTS $IMAGES_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$ACTOR_ID INTEGER," +
                    "$IMAGE TEXT)")

        val actorMoviesTable =
            ("CREATE TABLE IF NOT EXISTS $MOVIE_WITH_ACTOR_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$MOVIE_OR_SERIAL_ID INTEGER," +
                    "$ACTOR_ID INTEGER," +
                    "$TITLE TEXT," +
                    "$RATING DOUBLE," +
                    "$IMAGE TEXT)")

        val favoriteTable =
            ("CREATE TABLE IF NOT EXISTS $FAVORITE_TABLE (" +
                    "$ID INTEGER PRIMARY KEY," +
                    "$USER_ID INTEGER," +
                    "$MOVIE_OR_SERIAL_ID INTEGER," +
                    "$TITLE TEXT," +
                    "$IMAGE TEXT," +
                    "$TYPE TEXT)")

        db?.execSQL(userTable)
        db?.execSQL(informationTable)
        db?.execSQL(videoTable)
        db?.execSQL(movieTable)
        db?.execSQL(actorSquadTable)
        db?.execSQL(actorTable)
        db?.execSQL(actorImageTable)
        db?.execSQL(actorMoviesTable)
        db?.execSQL(favoriteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DETAILS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $ACTOR_SQUAD_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $RECOMMENDED_MOVIE_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $VIDEO_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $ACTOR_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $IMAGES_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $MOVIE_WITH_ACTOR_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $FAVORITE_TABLE")

        onCreate(db)
    }
}