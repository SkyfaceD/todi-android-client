package org.skyfaced.todi.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import org.skyfaced.todi.utils.enums.Theme

fun AppCompatActivity.setTheme(theme: Theme) {
    setTheme(theme.idRes)
    AppCompatDelegate.setDefaultNightMode(theme.mode)
}