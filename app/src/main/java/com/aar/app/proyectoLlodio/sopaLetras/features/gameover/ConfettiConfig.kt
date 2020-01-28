package com.aar.app.proyectoLlodio.sopaLetras.features.gameover

/**
 * ConfettiConfig is a simple data class to set some
 * specific configurations for confetti upon creation
 */
data class ConfettiConfig(
    var fadeOut: Boolean = false,
    var timeToLive: Long = 2000L
)
