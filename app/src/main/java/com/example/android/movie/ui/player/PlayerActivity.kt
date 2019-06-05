package com.example.android.movie.ui.player

import android.os.Bundle
import com.example.android.movie.R
import com.example.android.network.Constants.YOUTUBE_API_KEY
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity: YouTubeBaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initYouTubePlayer()
    }

    private fun initYouTubePlayer() {
        youtube_player.initialize(YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                wasRestored: Boolean
            ) {
                val videoId = intent.getStringExtra("VIDEO_ID")

                if (!wasRestored) {
                    player?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    player?.loadVideo(videoId)
                } else{
                    player?.play()
                }
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                errorReason: YouTubeInitializationResult?
            ) {
                if (errorReason!!.isUserRecoverableError) {
                    errorReason.getErrorDialog(this@PlayerActivity, 1).show()
                }
            }
        })
    }
}