package com.rcudev.simplemediaplayer.common.ui

sealed class Destination(val route: String) {
    object Main: Destination("main")
    object Secondary: Destination("secondary")
}