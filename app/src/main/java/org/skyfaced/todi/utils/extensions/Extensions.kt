package org.skyfaced.todi.utils.extensions

import com.skydoves.colorpickerview.ColorEnvelope
import kotlin.random.Random

//FIXME remove
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomString(length: Int = 50) = (1..length)
    .map { Random.nextInt(0, charPool.size) }
    .map { charPool[it] }
    .joinToString("")

val ColorEnvelope.hexCodeWithReshetochkots: String get() = "#${hexCode}"

fun <T> lazyUnsafe(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }