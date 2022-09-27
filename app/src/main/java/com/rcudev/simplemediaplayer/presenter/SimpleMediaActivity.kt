package com.rcudev.simplemediaplayer.presenter

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rcudev.player_service.service.SimpleMediaService
import com.rcudev.player_service.service.SimpleMediaServiceHandler
import com.rcudev.player_service.service.SimpleMediaState
import com.rcudev.simplemediaplayer.ui.theme.SimpleMediaPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SimpleMediaActivity : ComponentActivity() {

    @Inject
    lateinit var simpleMediaServiceHandler: SimpleMediaServiceHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleMediaPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val simpleMediaState = simpleMediaServiceHandler.simpleMediaState.collectAsState()
                    SimpleMediaPlayerScreen(
                        simpleMediaState = simpleMediaState.value,
                        startService = { startService() },
                        initPlayer = { simpleMediaServiceHandler.initPlayer("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3") },
                        playPause = simpleMediaServiceHandler::playPause,
                        backward = simpleMediaServiceHandler::backward,
                        forward = simpleMediaServiceHandler::forward
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SimpleMediaService::class.java))
    }

    private fun startService() {
        val intent = Intent(this, SimpleMediaService::class.java)
        startForegroundService(intent)
    }
}

@Composable
private fun SimpleMediaPlayerScreen(
    simpleMediaState: SimpleMediaState,
    startService: () -> Unit,
    initPlayer: () -> Unit,
    playPause: () -> Unit,
    forward: () -> Unit,
    backward: () -> Unit
) {
    val playPauseIconState = when (simpleMediaState) {
        SimpleMediaState.StatePlay -> android.R.drawable.ic_media_pause
        else -> android.R.drawable.ic_media_play
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = startService) {
            Text(text = "Start Service")
        }
        Button(onClick = initPlayer) {
            Text(text = "Init Player")
        }

        Spacer(modifier = Modifier.padding(bottom = 48.dp))

        if (simpleMediaState == SimpleMediaState.StateLoading) {
            CircularProgressIndicator()
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = backward) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_media_rew),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = playPause) {
                    Icon(
                        painter = painterResource(id = playPauseIconState),
                        contentDescription = ""
                    )
                }
                IconButton(onClick = forward) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_media_ff),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}