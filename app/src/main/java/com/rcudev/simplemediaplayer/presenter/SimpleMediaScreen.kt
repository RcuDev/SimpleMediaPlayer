package com.rcudev.simplemediaplayer.presenter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onStartService) {
            Text(text = "Start Service")
        }
        Button(onClick = vm::initPlayer) {
            Text(text = "Set play media")
        }
        Spacer(modifier = Modifier.padding(bottom = 48.dp))

        when (state.value) {
            UIState.Initial -> CircularProgressIndicator()
            is UIState.Ready -> SimpleMediaPlayerUI(
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