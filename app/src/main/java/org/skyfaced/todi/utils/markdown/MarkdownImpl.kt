package org.skyfaced.todi.utils.markdown

import android.content.Context
import android.widget.TextView
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.core.CorePlugin
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.handler.EmphasisEditHandler
import io.noties.markwon.editor.handler.StrongEmphasisEditHandler
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.skyfaced.todi.models.cell.Cell
import org.skyfaced.todi.models.markdown.Crop
import org.skyfaced.todi.utils.enums.Wrapper
import org.skyfaced.todi.utils.extensions.isLink
import org.skyfaced.todi.utils.extensions.isMultiline
import org.skyfaced.todi.utils.extensions.space
import org.skyfaced.todi.utils.extensions.wrapWith

class MarkdownImpl(applicationContext: Context) : Markdown {
    private val markwon = Markwon.builder(applicationContext)
        .usePlugin(CorePlugin.create())
        .usePlugin(TaskListPlugin.create(applicationContext))
        .usePlugin(TablePlugin.create(applicationContext))
        .usePlugin(SoftBreakAddsNewLinePlugin.create())
        .usePlugin(StrikethroughPlugin.create())
        .build()

    override val editor = MarkwonEditor.builder(markwon)
        .useEditHandler(EmphasisEditHandler())
        .useEditHandler(StrongEmphasisEditHandler())
        .build()

    override fun setMarkdown(textView: TextView, markdown: String) {
        markwon.setMarkdown(textView, markdown)
    }

    override fun headingWrap(
        crop: Crop,
        repeatCount: Int,
        wrap: (wrapped: String, selection: Int) -> Unit
    ) {
        val wrapper = Wrapper.Heading
        val wrapped = wrapper.value.repeat(repeatCount) space crop.text

        val selection = crop.start + wrapped.length

        wrap(wrapped, selection)
    }

    /**
     * @throws IllegalArgumentException if wrapper not any of
     * ([Wrapper.Bold], [Wrapper.Italic], [Wrapper.Strike])
     */
    override fun simpleWrap(
        crop: Crop,
        wrapper: Wrapper,
        wrap: (wrapped: String, selection: Int) -> Unit
    ) {
        if (wrapper.binary and Wrapper.simple == 0) throw IllegalArgumentException(
            "Not a simple wrapper"
        )

        val wrapped = crop.text wrapWith wrapper

        val skipCount = if (crop.start != crop.end) wrapped.length else wrapped.length / 2
        val selection = crop.start + skipCount

        wrap(wrapped, selection)
    }

    override fun codeWrap(
        crop: Crop,
        wrap: (wrapped: String, selection: Int) -> Unit
    ) {
        val cropped = crop.text
        val wrapped = if (!cropped.isMultiline()) {
            cropped wrapWith Wrapper.Code
        } else {
            val wrapper = Wrapper.MultiCode
            "${wrapper.value}\n" + cropped + "\n${wrapper.value}"
        }

        val skipCount = if (crop.start != crop.end) wrapped.length else wrapped.length / 2
        val selection = crop.start + skipCount

        wrap(wrapped, selection)
    }

    override fun linkWrap(
        crop: Crop,
        wrap: (wrapped: String, selection: IntRange) -> Unit
    ) {
        val cropped = crop.text
        val wrapped = formatList(cropped)

        val selection = when {
            cropped.isEmpty() || cropped.isLink() -> (crop.start + 1..crop.start + 5)
            else -> {
                val start = crop.start + wrapped.length - 4
                start..start + 3
            }
        }

        wrap(wrapped, selection)
    }

    private val link = "[%s](%s)"
    private fun formatList(word: String): String {
        if (word.isEmpty()) return link.format("text", "url")
        if (word.isLink()) return link.format("text", word)
        return link.format(word, "url")
    }

    override fun listWrap(
        crop: Crop,
        wrapper: Wrapper,
        wrap: (wrapped: String, selection: Int) -> Unit
    ) {
        val wrapped = formatList(wrapper, crop.text)

        val selection = crop.start + wrapped.length

        wrap(wrapped, selection)
    }

    private fun formatList(wrapper: Wrapper, word: String): String {
        val words = word.split("\n")

        if (words.size == 1) return wrapper.value space word

        if (wrapper != Wrapper.NumberedList) {
            return words.joinToString(
                prefix = "${wrapper.value} ",
                separator = "\n${wrapper.value} "
            )
        }

        var result = ""
        words.forEachIndexed { index, s ->
            result += "${index.inc()}. $s\n"
        }
        return result
    }

    override fun tableWrap(
        crop: Crop,
        cell: Cell,
        wrap: (wrapped: String, selection: Int) -> Unit
    ) {
        val wrapped = formatTable(cell.ratioX, cell.ratioY)

        val selection = crop.start + 2

        wrap(wrapped, selection)
    }

    private fun formatTable(x: Int, y: Int): String {
        val sb = StringBuilder()
        for (j in 0..y) {
            sb.append("|  ".repeat(x).plus("|\n"))
        }
        return sb.toString().replaceFirst("\n", "\n${"|---".repeat(x)}|\n")
    }
}