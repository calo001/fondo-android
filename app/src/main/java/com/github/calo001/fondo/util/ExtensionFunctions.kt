package com.github.calo001.fondo.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import com.github.calo001.fondo.R

fun Activity.makeStatusBarTransparent(color: Int) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = color
        }
    }
}

fun View.setMarginTop(marginTop: Int){
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun View.showWithAnimation(anim: Int = R.anim.fade_in) {
    val animation = AnimationUtils.loadAnimation(this.context, anim)
    this.visibility = View.VISIBLE
    this.startAnimation(animation)
}

fun View.hideWithAnimation(anim: Int = R.anim.fade_out) {
    val animation = AnimationUtils.loadAnimation(this.context, anim)
    startAnimation(animation)
    visibility = View.GONE
}