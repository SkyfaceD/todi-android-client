package org.skyfaced.todi.utils.markdown

/**
 * Contains additional information about selected text from EditText
 * @param text substring selected text
 * @param start selection start
 * @param end selection end
 */
data class Crop(
    val text: String,
    val start: Int,
    val end: Int
)