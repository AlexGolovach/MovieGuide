package com.example.android.movie.ui.main.moviedetails.actor

import ImageLoader
import android.graphics.Bitmap
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.Injector
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback

class ActorPresenter(private var iActorView: IActorView?) :
    IActorPresenter {

    override fun onDownloadActorDetails(actorId: Int) {
        iActorView?.showLoading()

        Injector.getActorsRepositoryImpl()
            .getInformationAboutActor(actorId, object : ActorsCallback<Actor> {
                override fun onSuccess(result: Actor) {
                    val imageUrl = result.image?.let { getImageUrl(it) }

                    imageUrl?.let {
                        ImageLoader.getInstance()?.load(it, object : Callback {
                            override fun onSuccess(url: String, bitmap: Bitmap) {
                                iActorView?.onDownloadResultDetails(result, bitmap)
                            }

                            override fun onError(url: String, throwable: Throwable) {
                                iActorView?.onDownloadDetailsError(throwable)
                                iActorView?.hideLoading()
                            }
                        })
                    }
                }

                override fun onError(throwable: Throwable) {
                    iActorView?.onDownloadDetailsError(throwable)
                    iActorView?.hideLoading()
                }
            })
    }

    override fun onDownloadImageURLs(actorId: Int) {
        Injector.getActorsRepositoryImpl()
            .getActorImages(actorId, object : ActorsCallback<ActorImages> {
                override fun onSuccess(result: ActorImages) {
                    iActorView?.onDownloadImageURLs(result)
                }

                override fun onError(throwable: Throwable) {
                    iActorView?.onDownloadDetailsError(throwable)
                    iActorView?.hideLoading()
                }
            })
    }

    override fun onDownloadActorMovies(actorId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getActorMovies(actorId, object : MoviesCallback<ActorMovies> {
                override fun onSuccess(result: ActorMovies) {
                    iActorView?.onDownloadActorMovies(result)
                    iActorView?.hideLoading()
                }

                override fun onError(throwable: Throwable) {
                    iActorView?.onDownloadDetailsError(throwable)
                    iActorView?.hideLoading()
                }
            })
    }

    override fun onDestroy() {
        iActorView = null
    }
}