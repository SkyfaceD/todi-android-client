package org.skyfaced.todi.utils.enums

import org.skyfaced.todi.R

enum class CornerFamily {
    ROUND,
    CUT,
    SQUARE;

    val resId: Int
        get() {
            return when (this) {
                ROUND -> R.id.btn_corner_round
                CUT -> R.id.btn_corner_cut
                SQUARE -> R.id.btn_corner_square
            }
        }

    companion object {
        fun fromId(resId: Int): CornerFamily {
            return when (resId) {
                R.id.btn_corner_round -> ROUND
                R.id.btn_corner_cut -> CUT
                R.id.btn_corner_square -> SQUARE
                else -> throw IllegalArgumentException("Can't handle button with $resId id")
            }
        }
    }
}