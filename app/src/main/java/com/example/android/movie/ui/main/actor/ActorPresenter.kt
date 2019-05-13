package com.example.android.movie.ui.main.actor

import ImageLoader
import android.graphics.Bitmap
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.network.Injector
import com.example.android.network.getImageUrl
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.movie.Movie
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback

class ActorPresenter(private var iActorView: IActorView?) :
    IActorPresenter {

    override fun onDownloadActorDetails(actorId: Long) {
        Injector.getActorsRepositoryImpl()
            .getInformationAboutActor(actorId, object : ActorsCallback<Actor> {
                override fun onSuccess(result: Actor) {
                    val imageUrl = getImageUrl(result.image)

                    ImageLoader.getInstance()?.load(imageUrl, object : Callback {
                        override fun onSuccess(url: String, bitmap: Bitmap) {
                            iActorView?.onDownloadResultDetails(result, bitmap)
                            iActorView?.hideLoading()
                        }

                        override fun onError(url: String, throwable: Throwable) {
                            iActorView?.showLoading()
                        }
                    })
                }

                override fun onError(throwable: Throwable) {
                    iActorView?.onDownloadDetailsError(throwable)
                    iActorView?.showLoading()
                }
            })
    }

    override fun onDownloadImageURLs(actorId: Long) {
        Injector.getActorsRepositoryImpl().getActorImages(actorId, object : ActorsCallback<List<String>>{
            override fun onSuccess(result: List<String>) {
                iActorView?.onDownloadImageURLs(result)
            }

            override fun onError(throwable: Throwable) {
                iActorView?.onDownloadDetailsError(throwable)
            }
        })
    }

    override fun onDestroy() {
        iActorView = null
    }

    override fun onDownloadActorMovies(actorId: Long) {
        Injector.getMoviesRepositoryImpl().getActorMovies(actorId, object : MoviesCallback<List<Movie>>{
            override fun onSuccess(result: List<Movie>) {
                iActorView?.onDownloadActorMovies(result)
            }

            override fun onError(throwable: Throwable) {
                iActorView?.onDownloadDetailsError(throwable)
            }
        })
    }
}