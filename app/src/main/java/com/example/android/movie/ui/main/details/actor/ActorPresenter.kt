package com.example.android.movie.ui.main.details.actor

import com.example.android.database.DatabaseCallback
import com.example.android.database.DatabaseRepositoryManager
import com.example.android.database.model.Movie
import com.example.android.movie.App
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.NetworkRepositoryManager
import com.example.android.network.NetworkCallback
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.movie.actormovies.ActorMovies

class ActorPresenter(private var isOnline: Boolean, private var iActorView: IActorView?) :
    IActorPresenter {

    override fun onDownloadActorDetails(actorId: Int) {
        iActorView?.showLoading()

        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .loadActorInformation(actorId, object :
                    DatabaseCallback<com.example.android.database.model.Actor> {
                    override fun onSuccess(result: com.example.android.database.model.Actor) {

                        iActorView?.onDownloadResultDetails(result)
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadActorDetails(actorId)
                        }
                    }

                })
        }
    }

    private fun loadActorDetails(actorId: Int) {
        NetworkRepositoryManager.getActorsRepositoryImpl()
            .getInformationAboutActor(actorId, object : NetworkCallback<Actor> {
                override fun onSuccess(result: Actor) {
                    val imageUrl = result.image?.let { getImageUrl(it) }

                    val actor = com.example.android.database.model.Actor(
                        actorId, result.name, result.biography, imageUrl
                    )

                    iActorView?.onDownloadResultDetails(actor)

                    addActorInformationInBD(actor)
                }

                override fun onError(error: String) {
                    iActorView?.onDownloadDetailsError(error)
                    iActorView?.hideLoading()
                }
            })
    }

    private fun addActorInformationInBD(actor: com.example.android.database.model.Actor) {
        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .addActorInformation(actor)
        }
    }

    override fun onDownloadImageURLs(actorId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .loadActorImages(actorId, object : DatabaseCallback<List<String>> {
                    override fun onSuccess(result: List<String>) {
                        iActorView?.onDownloadImageURLs(result)
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadActorImages(actorId)
                        }
                    }
                })
        }
    }

    private fun loadActorImages(actorId: Int) {
        val imageList = mutableListOf<String>()

        NetworkRepositoryManager.getActorsRepositoryImpl()
            .getActorImages(actorId, object : NetworkCallback<ActorImages> {
                override fun onSuccess(result: ActorImages) {
                    for (i in 0 until result.profiles.size) {
                        result.profiles[i].image?.let { getImageUrl(it) }?.let { imageList.add(it) }
                    }

                    iActorView?.onDownloadImageURLs(imageList)

                    addImagesInBD(actorId, imageList)
                }

                override fun onError(error: String) {
                    iActorView?.onDownloadDetailsError(error)
                    iActorView?.hideLoading()
                }
            })
    }

    private fun addImagesInBD(actorId: Int, imageList: MutableList<String>) {
        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .addActorImages(actorId, imageList)
        }
    }

    override fun onDownloadActorMovies(actorId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .loadActorMovies(actorId, object : DatabaseCallback<List<Movie>> {
                    override fun onSuccess(result: List<Movie>) {
                        iActorView?.onDownloadActorMovies(result)
                        iActorView?.hideLoading()
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadActorMovies(actorId)
                        }
                    }
                })
        }
    }

    private fun loadActorMovies(actorId: Int) {
        val actorMoviesList = mutableListOf<Movie>()

        NetworkRepositoryManager.getMoviesRepositoryImpl()
            .getActorMovies(actorId, object : NetworkCallback<ActorMovies> {
                override fun onSuccess(result: ActorMovies) {
                    for (i in 0 until result.cast.size) {
                        val movie =
                            Movie(
                                result.cast[i].id,
                                actorId,
                                result.cast[i].title,
                                result.cast[i].rating,
                                result.cast[i].image?.let { getImageUrl(it) }
                            )

                        actorMoviesList.add(movie)
                    }

                    iActorView?.onDownloadActorMovies(actorMoviesList)

                    addActorMoviesInBD(actorId, actorMoviesList)

                    iActorView?.hideLoading()
                }

                override fun onError(error: String) {
                    iActorView?.onDownloadDetailsError(error)
                    iActorView?.hideLoading()
                }
            })
    }

    private fun addActorMoviesInBD(actorId: Int, actorMoviesList: MutableList<Movie>) {
        App.get()?.let {
            DatabaseRepositoryManager.getActorDatabaseRepositoryImpl(it)
                .addActorMovies(actorId, actorMoviesList)
        }
    }

    override fun onDestroy() {
        iActorView = null
    }
}