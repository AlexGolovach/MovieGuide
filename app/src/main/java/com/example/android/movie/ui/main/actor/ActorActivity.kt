package com.example.android.movie.ui.main.actor

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.View
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.movie.ui.main.moviedetails.MovieDetailsActivity
import com.example.android.movie.ui.widget.dialog.DialogImageFragment
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.movie.actormovies.Cast
import kotlinx.android.synthetic.main.activity_actor.*

class ActorActivity : AppCompatActivity(), IActorView {

    private lateinit var actorPresenter: IActorPresenter
    private lateinit var actorImageAdapter: ActorImageAdapter
    private lateinit var actorMovieAdapter: ActorMovieAdapter

    private var dialogImage = DialogImageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_actor)

        actorImageAdapter = ActorImageAdapter()
        actorMovieAdapter = ActorMovieAdapter()

        actorPresenter = ActorPresenter(this)

        getData()
        initRecyclerImages()
        initRecyclerMovies()
    }

    private fun getData() {
        val actorId = intent.getIntExtra("ACTOR_ID", 0)

        actorPresenter.onDownloadActorDetails(actorId)
        actorPresenter.onDownloadImageURLs(actorId)
        actorPresenter.onDownloadActorMovies(actorId)
    }

    private fun initRecyclerImages() {
        val context = recycler_view_actor_images.context

        recycler_view_actor_images.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))

            val listener = object : ActorImageAdapter.Listener {
                override fun onItemClicked(actorImage: ActorImages) {
                    val imageUrl = actorImage.image?.let { getImageUrl(it) }

                    val bundle = Bundle()
                    bundle.putString("IMAGE_URL", imageUrl)

                    dialogImage.arguments = bundle
                    dialogImage.show(supportFragmentManager, "dialog_image")
                }
            }

            actorImageAdapter.listener = listener
            adapter = actorImageAdapter
        }
    }

    private fun initRecyclerMovies() {
        val context = recycler_view_actor_movies.context

        recycler_view_actor_movies.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))

            val listener = object : ActorMovieAdapter.Listener {
                override fun onItemClicked(movie: Cast) {
                    val intent = Intent(this@ActorActivity, MovieDetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            actorMovieAdapter.listener = listener
            adapter = actorMovieAdapter
        }
    }

    override fun onDownloadResultDetails(actor: Actor, image: Bitmap) {
        actor_image.setImageBitmap(image)
        actor_biography.text = actor.biography
    }

    override fun onDownloadImageURLs(images: List<ActorImages>) {
        actorImageAdapter.setItems(images)
    }

    override fun onDownloadActorMovies(movies: ActorMovies) {
        actorMovieAdapter.setItems(movies)
    }

    override fun onDownloadDetailsError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        actor_image.visibility = View.VISIBLE
        actor_biography.visibility = View.VISIBLE
        recycler_view_actor_images.visibility = View.VISIBLE
        actor_movies_text.visibility = View.VISIBLE
        recycler_view_actor_movies.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        actorPresenter.onDestroy()

        super.onDestroy()
    }
}