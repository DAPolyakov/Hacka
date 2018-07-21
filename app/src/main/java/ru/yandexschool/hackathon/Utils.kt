package ru.yandexschool.hackathon

import android.os.Build
import android.view.Window
import android.view.WindowManager

class Utils{
    companion object {
        fun setUpStatusBarColor(window: Window, color: Int){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            }
        }
    }
}