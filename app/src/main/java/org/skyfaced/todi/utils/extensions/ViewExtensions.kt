package org.skyfaced.todi.utils.extensions

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.constraintlayout.widget.Guideline

fun Guideline.setPercentByOrientation(
    orientation: Int,
    portrait: Float = .4f,
    landscape: Float = .25f
) {
    when (orientation) {
        ORIENTATION_PORTRAIT -> setGuidelinePercent(portrait)
        ORIENTATION_LANDSCAPE -> setGuidelinePercent(landscape)
    }
}