package org.skyfaced.todi.utils.extensions

fun <T> lazyUnsafe(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }