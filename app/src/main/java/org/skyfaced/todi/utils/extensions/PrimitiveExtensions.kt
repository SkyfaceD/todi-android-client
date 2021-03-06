package org.skyfaced.todi.utils.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.webkit.URLUtil
import org.skyfaced.todi.utils.enums.Wrapper
import java.text.SimpleDateFormat
import java.util.*

//TODO Timezone
fun Long.toDate(pattern: String = "EEEE, dd MMMM yyyy hh:mm aaa"): String {
    val date = Date(this)
    val locale = Locale.getDefault()

    return SimpleDateFormat(pattern, locale).format(date)
}

fun Float.dpToPx(context: Context): Float {
    return this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.pxToDp(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

infix fun String.wrapWith(wrapper: Wrapper): String = wrapper.value + this + wrapper.value

infix fun String.space(str: String): String = "$this $str"

fun String.isLink() = URLUtil.isValidUrl(this)

fun String.isMultiline(): Boolean {
    return contains("\n")
}