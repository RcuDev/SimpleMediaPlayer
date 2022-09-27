package com.rcudev.simplemediaplayer.presenter

import androidx.lifecycle.ViewModel
import com.rcudev.player_service.service.SimpleMediaServiceHandler
import com.rcudev.player_service.service.SimpleMediaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SimpleMediaViewModel @Inject constructor(
    val simpleMediaServiceHandler: SimpleMediaServiceHandler
): ViewModel()