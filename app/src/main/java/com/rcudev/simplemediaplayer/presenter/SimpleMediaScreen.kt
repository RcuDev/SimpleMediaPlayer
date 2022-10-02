package com.rcudev.simplemediaplayer.presenter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rcudev.simplemediaplayer.presenter.components.SimpleMediaPlayerUI

@Composable
internal fun SimpleMediaScreen(
    vm: SimpleMediaViewModel,
    onStartService: () -> Unit
) {
    val state = vm.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (state.value) {
            UIState.Initial -> CircularProgressIndicator()
            is UIState.Ready -> {
                onStartService()
                SimpleMediaPlayerUI(
                    durationString = vm.formatDuration(vm.duration),
                    playResourceProvider = {
                        if (vm.isPlaying) android.R.drawable.ic_media_pause
                        else android.R.drawable.ic_media_play
                    },
                    progressProvider = { Pair(vm.progress, vm.progressString) },
                    onUiEvent = vm::onUIEvent
                )
            }
        }
    }
}