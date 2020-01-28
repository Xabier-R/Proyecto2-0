package com.aar.app.proyectoLlodio.sopaLetras.features.gameover



/**
 * Created by dionsegijn on 5/31/17.
 */
interface OnParticleSystemUpdateListener {
    fun onParticleSystemStarted(view: KonfettiView, system: ParticleSystem, activeSystems: Int)
    fun onParticleSystemEnded(view: KonfettiView, system: ParticleSystem, activeSystems: Int)
}
