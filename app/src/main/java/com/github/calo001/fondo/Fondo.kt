package com.github.calo001.fondo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.SwitchPreferenceCompat
import com.github.calo001.fondo.manager.sharedpreferences.FondoSharePreferences

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

        val current = getCurrentDarkMode()

        AppCompatDelegate.setDefaultNightMode(current)
    }

    fun getCurrentDarkMode(): Int = FondoSharePreferences.getDarkMode()
}