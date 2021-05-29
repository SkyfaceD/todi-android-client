package org.skyfaced.todi.utils.extensions

import android.content.Context
import android.util.DisplayMetrics
import com.skydoves.colorpickerview.ColorEnvelope
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

//FIXME remove
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomString(length: Int = 50) = (1..length)
    .map { Random.nextInt(0, charPool.size) }
    .map { charPool[it] }
    .joinToString("")

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

val ColorEnvelope.hexCodeWithReshetochkots: String
    get() {
        return "#${hexCode}"
    }