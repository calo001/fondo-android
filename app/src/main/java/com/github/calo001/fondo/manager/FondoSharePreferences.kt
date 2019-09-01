package com.github.calo001.fondo.manager

import android.content.Context
import android.content.SharedPreferences
import com.github.calo001.fondo.Fondo
import com.github.calo001.fondo.config.Constants.SP_FILE
import com.github.calo001.fondo.config.Constants.SP_KEY_NOTIFICATION_COUNT

object FondoSharePreferences {
    private val sharedPreferences: SharedPreferences = Fondo.getInstance()
        .getSharedPreferences(SP_FILE, Context.MODE_PRIVATE)

    fun getNotificationCount(): Int {
        return sharedPreferences.getInt(SP_KEY_NOTIFICATION_COUNT, 0)
    }

    fun updateNotificationCount(value: Int) {
        sharedPreferences.edit().putInt(SP_KEY_NOTIFICATION_COUNT, value).apply()
    }

    fun getNextNotificationCount(): Int {
        val next = getNotificationCount() + 1
        updateNotificationCount(next)
        return next
    }
}