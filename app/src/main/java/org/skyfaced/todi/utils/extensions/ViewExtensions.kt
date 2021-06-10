package org.skyfaced.todi.utils.extensions

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.widget.EditText
import androidx.constraintlayout.widget.Guideline
import org.skyfaced.todi.models.markdown.Crop

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

val EditText.start: Int get() = selectionStart.coerceAtMost(selectionEnd)

val EditText.end: Int get() = selectionStart.coerceAtLeast(selectionEnd)

fun EditText.crop(): Crop {
    val start = start
    val end = end
    return Crop(text.substring(start, end), start, end)
}

fun EditText.replace(replacement: String) {
    text.replace(start, end, replacement)
}