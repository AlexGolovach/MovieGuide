package com.example.android.movie.ui.main.search

import com.example.android.movie.mvp.search.ISearchMoviesPresenter
import com.example.android.movie.mvp.search.ISearchMoviesView
import com.example.android.network.Injector
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import com.example.android.network.repository.movies.MoviesCallback

class SearchPresenter(private var iSearchMoviesView: ISearchMoviesView?) :
    ISearchMoviesPresenter {

    override fun onSearchMovies(query: String?) {

        val userInput = query?.toLowerCase()

        query?.let {
            if (it.length >= 3) {
                Injector.getMoviesRepositoryImpl()
                    .searchMovie(query, object :
                        MoviesCallback<MovieList> {
                        override fun onSuccess(result: MovieList) {
                            for (movie in result.results) {
                                if (movie.title?.toLowerCase()!!.contains(userInput.toString())) {
                                    iSearchMoviesView?.onSearchResult(result)
                                }
                            }
                        }

                        override fun onError(throwable: Throwable) {
                            iSearchMoviesView?.onSearchError(throwable)
                        }
                    })
            }
        }
    }

    override fun onDestroy(){
        iSearchMoviesView = null
    }
}