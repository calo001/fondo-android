package com.github.calo001.fondo

import android.app.Application

class Fondo : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Fondo? = null
        fun getInstance(): Fondo = instance as Fondo
    }

    override fun onCreate() {
        super.onCreate()
    }
}