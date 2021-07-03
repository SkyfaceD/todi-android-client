package org.skyfaced.todi.utils.enums

enum class Wrapper(val value: String) {
    Bold("**"),
    Italic("_"),
    Strike("~~"),
    Code("`"),
    MultiCode("```"),
    Heading("#"),
    BulletedList("-"),
    NumberedList("1."),
    TaskList("- [ ]");

    val binary: Int = 1 shl ordinal

    companion object {
        val simple = Bold.binary or Italic.binary or Strike.binary
    }
}