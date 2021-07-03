package org.skyfaced.todi.utils.extensions

import android.content.Context
import android.util.DisplayMetrics
import org.skyfaced.todi.utils.enums.Wrapper
import java.text.SimpleDateFormat
import java.util.*

//TODO Timezone
fun Long.toDate(pattern: String = "EEEE, dd MMMM yyyy hh:mm aaa"): String {
    val date = Date(this)
    val locale = Locale.getDefault()

    return SimpleDateFormat(pattern, locale).format(date)
}

fun Float.dp(context: Context): Float {
    return this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.px(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

//TODO
///**
// * @return [UUID] if it valid, otherwise empty string
// */
//fun String.asUUID() = try { UUID.fromString(this) } catch (e: Exception) { null }

fun String.asUUID() = UUID.fromString(this)

infix fun String.wrapWith(wrapper: Wrapper): String = wrapper.value + this + wrapper.value