package com.rcudev.simplemediaplayer.secondary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rcudev.simplemediaplayer.common.ui.SimpleMediaViewModel
import com.rcudev.simplemediaplayer.common.ui.components.SimpleMediaPlayerUI

@Composable
fun SecondaryScreen(
    vm: SimpleMediaViewModel
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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