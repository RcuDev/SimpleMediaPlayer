package com.rcudev.player_service.service

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SimpleMediaServiceHandler @Inject constructor(
    private val player: ExoPlayer
): Player.Listener {

    private val _simpleMediaState = MutableStateFlow<SimpleMediaState>(SimpleMediaState.StateLoading)
    val simpleMediaState = _simpleMediaState.asStateFlow()

    init {
        player.addListener(this)
    }

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

    override fun onIsLoadingChanged(isLoading: Boolean) {
        _simpleMediaState.value = if (isLoading) SimpleMediaState.StateLoading else SimpleMediaState.StateReady
        super.onIsLoadingChanged(isLoading)
    }
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _simpleMediaState.value = if (isPlaying) SimpleMediaState.StatePlay else SimpleMediaState.StatePause
        super.onIsPlayingChanged(isPlaying)
    }
}

sealed interface SimpleMediaState {
    object StateLoading : SimpleMediaState
    object StateReady : SimpleMediaState
    object StatePlay : SimpleMediaState
    object StatePause : SimpleMediaState
}