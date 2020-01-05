package org.mightyfrog.android.exoplayersample

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackPreparer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.playerView

class MainActivity : AppCompatActivity(), PlaybackPreparer {

    private lateinit var player: SimpleExoPlayer
    private val uri: Uri = Uri.parse("https://v.redd.it/hgvyxj9pts841/DASHPlaylist.mpd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "Test"))
        val mediaSource = DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        player = ExoPlayerFactory.newSimpleInstance(this)
        playerView.player = player
        playerView.setPlaybackPreparer(this)
        player.prepare(mediaSource)
    }

    override fun onResume() {
        super.onResume()

        player.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()

        player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        player.release()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event)
    }

    override fun preparePlayback() {
        player.retry()
    }
}
