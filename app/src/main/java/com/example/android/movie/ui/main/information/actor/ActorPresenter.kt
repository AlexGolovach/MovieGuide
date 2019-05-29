package com.example.android.movie.ui.main.information.actor

import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.network.Injector
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.shows.actorshows.ActorShows
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback
import com.example.android.network.repository.shows.ShowsCallback

class ActorPresenter(private var iActorView: IActorView?) :
    IActorPresenter {

    override fun onDownloadActorDetails(actorId: Int) {
        iActorView?.showLoading()

        Injector.getActorsRepositoryImpl()
            .getInformationAboutActor(actorId, object : ActorsCallback<Actor> {
                override fun onSuccess(result: Actor) {
                    iActorView?.onDownloadResultDetails(result)
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
                }

                override fun onError(throwable: Throwable) {
                    iActorView?.onDownloadDetailsError(throwable)
                    iActorView?.hideLoading()
                }
            })
    }

    override fun onDownloadActorShows(actorId: Int) {
        Injector.getShowsRepositoryImpl().getActorShows(actorId, object : ShowsCallback<ActorShows>{
            override fun onSuccess(result: ActorShows) {
                iActorView?.onDownloadActorShows(result)
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