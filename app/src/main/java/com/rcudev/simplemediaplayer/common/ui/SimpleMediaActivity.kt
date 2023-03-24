package com.rcudev.simplemediaplayer.common.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rcudev.player_service.service.SimpleMediaService
import com.rcudev.simplemediaplayer.common.ui.theme.SimpleMediaPlayerTheme
import com.rcudev.simplemediaplayer.main.SimpleMediaScreen
import com.rcudev.simplemediaplayer.secondary.SecondaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleMediaActivity : ComponentActivity() {

    private val viewModel: SimpleMediaViewModel by viewModels()
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SimpleMediaPlayerTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Destination.Main.route) {
                    composable(Destination.Main.route) {
                        SimpleMediaScreen(
                            vm = viewModel,
                            navController = navController,
                            startService = ::startService
                        )
                    }
                    composable(Destination.Secondary.route) {
                        SecondaryScreen(vm = viewModel)
                    }
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