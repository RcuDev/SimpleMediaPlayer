package com.rcudev.simplemediaplayer.presenter

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rcudev.player_service.service.SimpleMediaService
import com.rcudev.simplemediaplayer.ui.theme.SimpleMediaPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleMediaActivity : ComponentActivity() {

    private val viewModel: SimpleMediaViewModel by viewModels()
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleMediaPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleMediaScreen(
                        vm = viewModel,
                        onStartService = { startService() }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SimpleMediaService::class.java))
        isServiceRunning = false
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}