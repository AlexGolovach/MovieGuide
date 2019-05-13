package com.example.android.network.repository.movies

import android.util.Log
import com.example.android.network.Constants
import com.example.android.network.httprequest.Callback
import com.example.android.network.httprequest.HttpRequest
import com.example.android.network.models.Genres
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieDetails
import org.json.JSONObject
import java.lang.NullPointerException

class MoviesRepositoryImpl : MoviesRepository {

    private val topMovies = mutableListOf<Movie>()
    private val searchResultMovie = mutableListOf<Movie>()
    private val actorMoviesList = mutableListOf<Movie>()
    private val recommendedMoviesList = mutableListOf<Movie>()

    private lateinit var movieDetails: MovieDetails

    private lateinit var url: String

    override fun loadMovies(callback: MoviesCallback<List<Movie>>) {
        topMovies.clear()

        url =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)
                val moviesArray = jsonObject.getJSONArray("results")

                for (i in 0 until moviesArray.length()) {
                    val movie = moviesArray.getJSONObject(i)

                    val movieId = movie.getLong("id")
                    val movieRating = movie.getDouble("vote_average")
                    val movieName = movie.getString("title")
                    val movieDescription = movie.getString("overview")
                    val moviePoster = movie.getString("poster_path")

                    topMovies.add(
                        Movie(
                            movieId,
                            movieName,
                            moviePoster,
                            movieDescription,
                            movieRating
                        )
                    )
                }

                if (topMovies.size != 0) {
                    callback.onSuccess(topMovies)
                } else {
                    callback.onError(NullPointerException("We have some problems with download movies"))
                }
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun searchMovie(query: String, callback: MoviesCallback<List<Movie>>) {
        searchResultMovie.clear()

        url =
            "https://api.themoviedb.org/3/search/movie?api_key=${Constants.API_KEY}&language=en-US&query=$query&page=1&include_adult=false"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val countSearchResult = jsonObject.getInt("total_results")

                if (countSearchResult != 0) {
                    val moviesArray = jsonObject.getJSONArray("results")

                    for (i in 0 until moviesArray.length()) {
                        val movie = moviesArray.getJSONObject(i)

                        val movieId = movie.getLong("id")
                        val movieRating = movie.getDouble("vote_average")
                        val movieName = movie.getString("title")
                        val movieDescription = movie.getString("overview")
                        val moviePoster = movie.getString("poster_path")

                        searchResultMovie.add(
                            Movie(
                                movieId,
                                movieName,
                                moviePoster,
                                movieDescription,
                                movieRating
                            )
                        )

                        Log.d(
                            MoviesRepositoryImpl::class.java.simpleName, Movie(
                                movieId,
                                movieName,
                                moviePoster,
                                movieDescription,
                                movieRating
                            ).toString()
                        )
                    }

                    if (searchResultMovie.size != 0) {
                        callback.onSuccess(searchResultMovie)
                    } else {
                        callback.onError(NullPointerException("We have some problems with download movies"))
                    }
                } else {
                    callback.onError(NullPointerException("We have some problems with download movies"))
                }
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun getInformationAboutMovie(movieId: Long, callback: MoviesCallback<MovieDetails>) {
        url =
            "https://api.themoviedb.org/3/movie/$movieId?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val id = jsonObject.getLong("id")
                val title = jsonObject.getString("title")
                val description = jsonObject.getString("overview")
                val budget = jsonObject.getLong("budget")
                val rating = jsonObject.getDouble("vote_average")
                val homepage = jsonObject.getString("homepage")
                val release = jsonObject.getString("release_date")
                val image = jsonObject.getString("poster_path")

                val genresArray = jsonObject.getJSONArray("genres")

                for (i in 0 until genresArray.length()) {
                    val genre = genresArray.getJSONObject(i)

                    val genreId = genre.getInt("id")
                    val genreName = genre.getString("name")

                    movieDetails = MovieDetails(
                        Movie(
                            id,
                            title,
                            image,
                            description,
                            rating
                        ),
                        Genres(genreId, genreName),
                        budget,
                        homepage,
                        release
                    )
                }

                callback.onSuccess(movieDetails)
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun getActorMovies(actorId: Long, callback: MoviesCallback<List<Movie>>) {
        actorMoviesList.clear()

        url =
            "https://api.themoviedb.org/3/person/$actorId/movie_credits?api_key=${Constants.API_KEY}&language=en-US"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val castArray = jsonObject.getJSONArray("cast")

                for (i in 0 until castArray.length()) {
                    val movie = castArray.getJSONObject(i)

                    val id = movie.getLong("id")
                    val title = movie.getString("title")
                    val poster = movie.getString("poster_path")
                    val description = movie.getString("overview")
                    val rating = movie.getDouble("vote_average")

                    actorMoviesList.add(Movie(id, title, poster, description, rating))
                }

                if (actorMoviesList.size != 0) {
                    callback.onSuccess(actorMoviesList)
                } else {
                    callback.onError(NullPointerException("We have some problems with download actor movies"))
                }
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

    override fun getRecommendedMoviesForMovie(
        movieId: Long,
        callback: MoviesCallback<List<Movie>>
    ) {
        recommendedMoviesList.clear()

        url =
            "https://api.themoviedb.org/3/movie/$movieId/recommendations?api_key=${Constants.API_KEY}&language=en-US&page=1"

        HttpRequest.getInstance()?.load(url, object : Callback {
            override fun onSuccess(result: String) {
                val jsonObject = JSONObject(result)

                val resultsArray = jsonObject.getJSONArray("results")

                for (i in 0 until resultsArray.length()) {
                    val movie = resultsArray.getJSONObject(i)

                    val id = movie.getLong("id")
                    val title = movie.getString("title")
                    val poster = movie.getString("poster_path")
                    val description = movie.getString("overview")
                    val rating = movie.getDouble("vote_average")

                    recommendedMoviesList.add(Movie(id, title, poster, description, rating))
                }

                if (recommendedMoviesList.size != 0) {
                    callback.onSuccess(recommendedMoviesList)
                } else {
                    callback.onError(NullPointerException("We have some problems with download movies"))
                }
            }

            override fun onError(throwable: Throwable) {
                if (throwable is NullPointerException) {
                    Log.d(MoviesRepositoryImpl::class.java.simpleName, throwable.message)
                }
            }
        })
    }

}