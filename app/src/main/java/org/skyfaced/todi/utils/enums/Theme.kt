package org.skyfaced.todi.utils.enums

import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate.*
import org.skyfaced.todi.R

enum class Theme(
    @StyleRes val idRes: Int,
    @NightMode val mode: Int
) {
    DarkDefault(R.style.Theme_Dark_Default, MODE_NIGHT_YES),
    LightDefault(R.style.Theme_Light_Default, MODE_NIGHT_NO)
}