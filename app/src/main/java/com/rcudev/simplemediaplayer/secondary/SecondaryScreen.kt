package com.rcudev.simplemediaplayer.secondary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.rcudev.simplemediaplayer.common.ui.SimpleMediaViewModel
import com.rcudev.simplemediaplayer.common.ui.components.BottomPlayerUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryScreen(
    vm: SimpleMediaViewModel
) {

    Scaffold(
        bottomBar = {
            BottomPlayerUI(
                durationString = vm.formatDuration(vm.duration),
                playResourceProvider = {
                    if (vm.isPlaying) android.R.drawable.ic_media_pause
                    else android.R.drawable.ic_media_play
                },
                progressProvider = { Pair(vm.progress, vm.progressString) },
                onUiEvent = vm::onUIEvent
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {

            Image(
                painter = rememberAsyncImagePainter("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}