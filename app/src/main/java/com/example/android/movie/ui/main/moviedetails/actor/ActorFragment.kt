package com.example.android.movie.ui.main.moviedetails.actor

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.movie.ui.main.moviedetails.DetailsActivity
import com.example.android.movie.ui.utils.dialogimage.DialogImageFragment
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.models.actor.Actor
import com.example.android.network.models.actor.ActorImages
import com.example.android.network.models.actor.Image
import com.example.android.network.models.movie.actormovies.ActorMovies
import com.example.android.network.models.movie.actormovies.Cast
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_actor.*

class ActorFragment : Fragment(), IActorView {

    private lateinit var actorPresenter: IActorPresenter
    private lateinit var actorImageAdapter: ActorImageAdapter
    private lateinit var actorMovieAdapter: ActorMovieAdapter

    private var dialogImage = DialogImageFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_actor, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actorImageAdapter = ActorImageAdapter()
        actorMovieAdapter = ActorMovieAdapter()

        actorPresenter = ActorPresenter(this)

        getData()
        initRecyclerImages()
        initRecyclerMovies()
    }

    private fun getData() {
        //TODO save to variable
        val actorId = arguments?.getInt("ACTOR_ID")

        actorId?.let {
            actorPresenter.onDownloadActorDetails(it)
            actorPresenter.onDownloadImageURLs(it)
            actorPresenter.onDownloadActorMovies(it)
        }
    }

    private fun initRecyclerImages() {
        val context = recycler_view_actor_images.context

        recycler_view_actor_images.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object : ActorImageAdapter.Listener {
                override fun onItemClicked(actorImage: Image) {
                    //TODO prepare url before adapter
                    val imageUrl = actorImage.image?.let { getImageUrl(it) }

                    val bundle = Bundle()
                    bundle.putString("IMAGE_URL", imageUrl)
                    //TODO try to create newInstance method for fragment
                    dialogImage.arguments = bundle
                    dialogImage.show(fragmentManager, "dialog_image")
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

            val listener = object : ActorMovieAdapter.Listener {
                override fun onItemClicked(movie: Cast) {
                    val intent = Intent(activity, DetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            actorMovieAdapter.listener = listener
            adapter = actorMovieAdapter
        }
    }

    override fun onDownloadResultDetails(actor: Actor, image: Bitmap) {
        activity?.apply {
            poster_image?.setImageBitmap(image)
            collapsing_toolbar.title = actor.name
        }
        actor_biography.text = actor.biography
    }

    override fun onDownloadImageURLs(images: ActorImages) {
        actorImageAdapter.setItems(images)
    }

    override fun onDownloadActorMovies(movies: ActorMovies) {
        actorMovieAdapter.setItems(movies)
    }

    override fun onDownloadDetailsError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        activity?.collapsing_toolbar?.visibility = View.GONE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        actor_image.visibility = View.VISIBLE
        actor_biography.visibility = View.VISIBLE
        recycler_view_actor_images.visibility = View.VISIBLE
        actor_movies_text.visibility = View.VISIBLE
        recycler_view_actor_movies.visibility = View.VISIBLE
        activity?.collapsing_toolbar?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        actorPresenter.onDestroy()

        super.onDestroy()
    }
}