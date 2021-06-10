package org.skyfaced.todi.utils.markdown

import android.widget.TextView
import io.noties.markwon.editor.MarkwonEditor
import org.skyfaced.todi.models.cell.Cell
import org.skyfaced.todi.models.markdown.Crop
import org.skyfaced.todi.utils.enums.Wrapper

interface Markdown {
    val editor: MarkwonEditor

    fun setMarkdown(textView: TextView, markdown: String)

    fun headingWrap(
        crop: Crop,
        repeatCount: Int,
        wrap: (wrapped: String, selection: Int) -> Unit
    )

    fun simpleWrap(
        crop: Crop,
        wrapper: Wrapper,
        wrap: (wrapped: String, selection: Int) -> Unit
    )

    fun codeWrap(
        crop: Crop,
        wrap: (wrapped: String, selection: Int) -> Unit
    )

    fun linkWrap(
        crop: Crop,
        wrap: (wrapped: String, selection: IntRange) -> Unit
    )

    fun listWrap(
        crop: Crop,
        wrapper: Wrapper,
        wrap: (wrapped: String, selection: Int) -> Unit
    )

    fun tableWrap(
        crop: Crop,
        cell: Cell,
        wrap: (wrapped: String, selection: Int) -> Unit
    )
}