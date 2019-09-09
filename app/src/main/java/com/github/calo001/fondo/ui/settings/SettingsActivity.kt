package com.github.calo001.fondo.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.github.calo001.fondo.R
import com.github.calo001.fondo.manager.sharedpreferences.FondoSharePreferences
import com.github.calo001.fondo.ui.main.MainActivity
import com.github.calo001.fondo.util.makeStatusBarTransparent
import com.github.calo001.fondo.util.setMarginTop
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val settingsFragment = SettingsFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.settings, settingsFragment, SettingsFragment.TAG)
            .commit()

        setSupportActionBar(settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val color = ContextCompat.getColor(this, R.color.uiTransparent)
        makeStatusBarTransparent(color)
        ViewCompat.setOnApplyWindowInsetsListener(settingsContainer) { _, insets ->
            settingsToolbar.setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        var currentMode = getCurrentDarkMode()
        var newMode: Int? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val switch = findPreference<SwitchPreferenceCompat>("dark-mode")

            when (currentMode) {
                AppCompatDelegate.MODE_NIGHT_YES -> switch?.isChecked = true
                AppCompatDelegate.MODE_NIGHT_NO -> switch?.isChecked = false
            }

            switch?.setOnPreferenceClickListener {
                when (switch.isChecked) {
                    true -> {
                        newMode = AppCompatDelegate.MODE_NIGHT_YES
                        true
                    }
                    false -> {
                        newMode = AppCompatDelegate.MODE_NIGHT_NO
                        true
                    }
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()

            newMode?.let {
                if (newMode != currentMode) {
                    FondoSharePreferences.saveDarkMode(newMode!!)
                    AppCompatDelegate.setDefaultNightMode(newMode!!)

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }

        private fun getCurrentDarkMode(): Int = FondoSharePreferences.getDarkMode()

        companion object {
            const val TAG = "SettingsFragment"

            @JvmStatic
            fun newInstance() = SettingsFragment()
        }
    }
}