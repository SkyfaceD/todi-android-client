package org.skyfaced.todi.utils.extensions

fun <T> lazySafetyNone(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }