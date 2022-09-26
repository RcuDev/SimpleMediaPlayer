package com.rcudev.player_service.service

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import javax.inject.Inject

class SimpleMediaServiceHandler @Inject constructor(
    private val player: ExoPlayer
) : MediaController.Listener {

    fun initPlayer(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
    }

    fun playPause(): Int {
        return if (player.isPlaying) {
            player.pause()
            android.R.drawable.ic_media_play
        } else {
            player.play()
            android.R.drawable.ic_media_pause
        }
    }

    fun backward() {
        player.seekBack()
    }

    fun forward() {
        player.seekForward()
    }

}