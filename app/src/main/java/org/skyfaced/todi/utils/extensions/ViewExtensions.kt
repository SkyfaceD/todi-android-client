package org.skyfaced.todi.utils.extensions

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

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

val RecyclerView.LayoutManager.firstVisibleItemPosition: Int
    get() {
        return when (this) {
            is StaggeredGridLayoutManager -> findFirstCompletelyVisibleItemPositions(null)[0]
            is GridLayoutManager -> findFirstVisibleItemPosition()
            is LinearLayoutManager -> findFirstVisibleItemPosition()
            else -> throw IllegalArgumentException("Can't define layout manager $this")
        }
    }