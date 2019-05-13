package com.example.android.network.repository.actors

import com.example.android.network.Constants
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.movie.MovieActorSquad
import org.json.JSONObject
import java.lang.NullPointerException

class ActorsRepositoryImpl : ActorsRepository {

    private val actorsSquadList = mutableListOf<MovieActorSquad>()
    private val actorImagesList = mutableListOf<String>()

    private lateinit var actor: Actor

    private lateinit var url: String

    override fun getInformationAboutActor(actorId: Long, callback: ActorsCallback<Actor>) {
        url =
            "https://api.themoviedb.org/3/person/$actorId?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val id = jsonObject.getLong("id")
                val name = jsonObject.getString("name")
                val biography = jsonObject.getString("biography")
                val image = jsonObject.getString("profile_path")

                actor = Actor(id, name, biography, image)

                callback.onSuccess(actor)
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    callback.onError(NullPointerException("We have some problems with download information about actor"))
                }
            }
        })
    }

    override fun getActorImages(actorId: Long, callback: ActorsCallback<List<String>>) {
        actorImagesList.clear()

        url = "https://api.themoviedb.org/3/person/$actorId/images?api_key=${Constants.API_KEY}"

        HttpRequest.getInstance()?.load(url, object : Callback{
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val profilesArray = jsonObject.getJSONArray("profiles")

                for (i in 0 until profilesArray.length()){
                    val profiles = profilesArray.getJSONObject(i)

                    val image = profiles.getString("file_path")

                    actorImagesList.add(image)
                }

                if (actorImagesList.size != 0){
                    callback.onSuccess(actorImagesList)
                } else {
                    callback.onError(NullPointerException("We have some problems with download profiles"))
                }
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download profiles"))
            }
        })
    }

    override fun getActorsSquad(movieId: Long, callback: ActorsCallback<List<MovieActorSquad>>) {
        actorsSquadList.clear()

        url = "https://api.themoviedb.org/3/movie/$movieId/credits?api_key=${Constants.API_KEY}"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val castArray = jsonObject.getJSONArray("cast")

                for (i in 0 until castArray.length()) {
                    val actor = castArray.getJSONObject(i)

                    val id = actor.getLong("id")
                    val name = actor.getString("name")
                    val character = actor.getString("character")
                    val image = actor.getString("profile_path")

                    actorsSquadList.add(
                        MovieActorSquad(
                            id,
                            name,
                            character,
                            image
                        )
                    )
                }

                if (actorsSquadList.size != 0) {
                    callback.onSuccess(actorsSquadList)
                } else {
                    callback.onError(NullPointerException("We have some problems with download actor squad"))
                }
            }

            override fun onError(throwable: Throwable) {
                callback.onError(NullPointerException("We have some problems with download actor squad"))
            }
        })
    }
}