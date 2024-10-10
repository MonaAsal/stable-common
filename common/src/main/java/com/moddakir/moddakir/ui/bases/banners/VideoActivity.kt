package com.moddakir.moddakir.ui.bases.banners

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityVideoBinding
import com.moddakir.moddakir.network.model.base.BaseActivity

class VideoActivity : BaseActivity() {
    override val layoutId: Int get() = R.layout.activity_video
    private lateinit var binding: ActivityVideoBinding
    override fun initializeViewModel() {}
    override fun observeViewModel() {}

    companion object {
        var TITLE: String = "title"
        var URL: String = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video)
        setupToolbar()
        setupPlayer()
    }

    fun start(context: Context, url: String?, title: String?) {
        val intent = Intent(context, VideoActivity::class.java)
        intent.putExtra(TITLE, title)
        intent.putExtra(URL, url)
        context.startActivity(intent)

    }

    private fun setupPlayer() {
        val player = ExoPlayer.Builder(this).build()
        binding.epVideoView.player = player
        // Build the media item.
        val mediaItem = MediaItem.fromUri(intent.extras!!.getString(URL, ""))
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        //4
        player.playWhenReady = true
        // Start the playback.
        player.play()

    }

    private fun setupToolbar() {
        if (intent.extras == null) return
        val title = intent.extras!!.getString(TITLE, "")
        val tvTitle: TextView = findViewById(R.id.tv_title)
        tvTitle.text = title
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onDestroy() {
        if (binding.epVideoView != null) {
            binding.epVideoView.player!!.stop()
            binding.epVideoView.player!!.release()
        }
        super.onDestroy()
    }
}