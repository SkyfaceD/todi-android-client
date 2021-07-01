package org.skyfaced.todi.ui.detail

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.todi.R
import org.skyfaced.todi.database.entities.TaskEntity
import org.skyfaced.todi.databinding.DialogTableBinding
import org.skyfaced.todi.databinding.FragmentDetailBinding
import org.skyfaced.todi.models.cell.Cell
import org.skyfaced.todi.models.markdown.Crop
import org.skyfaced.todi.ui.custom.cell.CellPickerListener
import org.skyfaced.todi.utils.enums.Wrapper
import org.skyfaced.todi.utils.extensions.*
import org.skyfaced.todi.utils.markdown.Markdown
import java.util.*

@SuppressLint("RestrictedApi")
class DetailFragment : Fragment(R.layout.fragment_detail), CellPickerListener {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private var _tableBinding: DialogTableBinding? = null
    private val tableBinding get() = checkNotNull(_tableBinding) { "Binding isn't initialized" }
    private val viewModel: DetailViewModel by viewModel()
    private val markdown: Markdown by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //TODO edit mode
            lblTask.setText(R.string.add_task)

            markdown.setMarkdown(btnBold, "B" wrapWith Wrapper.Bold)
            markdown.setMarkdown(btnItalic, "I" wrapWith Wrapper.Italic)
            markdown.setMarkdown(btnStrike, "S" wrapWith Wrapper.Strike)

            btnHeading.setOnClickListener { headingMenu.show() }
            btnBold.setOnClickListener {
                markdown.simpleWrap(cropped, Wrapper.Bold, ::replaceAndSelect)
            }
            btnItalic.setOnClickListener {
                markdown.simpleWrap(cropped, Wrapper.Italic, ::replaceAndSelect)
            }
            btnStrike.setOnClickListener {
                markdown.simpleWrap(cropped, Wrapper.Strike, ::replaceAndSelect)
            }
            btnCode.setOnClickListener { markdown.codeWrap(cropped, ::replaceAndSelect) }
            btnLink.setOnClickListener { markdown.linkWrap(cropped, ::replaceAndSelect) }
            btnList.setOnClickListener { listMenu.show() }
            btnTable.setOnClickListener { tableDialog.show() }

            switchMarkdown.setOnCheckedChangeListener { _, isChecked ->
                edtTaskMarkdown.isVisible = !isChecked
                txtTaskMarkdown.isVisible = isChecked
                quickAccess.isInvisible = isChecked
                txtPreview.isVisible = isChecked

                //FIXME
                if (!isChecked) {
                    edtTaskMarkdown.requestFocus()
                } else {
                    markdown.setMarkdown(txtTaskMarkdown, edtTaskMarkdown.text.toString())
                }
            }

            edtTaskMarkdown.addTextChangedListener(
                MarkwonEditorTextWatcher.withProcess(
                    markdown.editor
                )
            )
        }
    }

    private val headingMenu: PopupMenu by lazyUnsafe {
        with(binding) {
            val popup = PopupMenu(requireContext(), btnHeading)
            popup.menuInflater.inflate(R.menu.heading_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                markdown.headingWrap(cropped, menuItem.order, ::replaceAndSelect)
                true
            }

            return@lazyUnsafe popup
        }
    }

    private val listMenu: PopupMenu by lazyUnsafe {
        val popup = PopupMenu(requireContext(), binding.btnList)
        popup.menuInflater.inflate(R.menu.list_menu, popup.menu)

        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8f,
                    resources.displayMetrics
                ).toInt()
                if (item.icon != null) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item.icon = InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0)
                    } else {
                        item.icon =
                            object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                                override fun getIntrinsicWidth(): Int {
                                    return intrinsicHeight + iconMarginPx + iconMarginPx
                                }
                            }
                    }
                }
            }
        }

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            val wrapper = when (menuItem.itemId) {
                R.id.list_bulleted -> Wrapper.BulletedList
                R.id.list_numbered -> Wrapper.NumberedList
                R.id.list_task -> Wrapper.TaskList
                else -> throw IllegalArgumentException("Can't define wrapper by ${menuItem.itemId} id")
            }
            markdown.listWrap(cropped, wrapper, ::replaceAndSelect)
            true
        }

        return@lazyUnsafe popup
    }

    private val tableDialog: AlertDialog by lazyUnsafe {
        return@lazyUnsafe MaterialAlertDialogBuilder(requireContext()).apply {
            _tableBinding = DialogTableBinding.inflate(layoutInflater, null, false)
            with(tableBinding) {
                cellPicker.cellPickerListener = this@DetailFragment
                txtCell.text = getString(R.string.table_counter, 1, 1)
                setView(root)
                setNegativeButton(R.string.cancel, null)
                setPositiveButton(R.string.ok) { _, _ ->
                    val cell = cellPicker.currentCell
                    markdown.tableWrap(cropped, cell, ::replaceAndSelect)
                }
                setOnDismissListener {
                    cellPicker.resetCell()
                    txtCell.text = getString(R.string.table_counter, 1, 1)
                }
            }
        }.create()
    }

    private val cropped: Crop get() = binding.edtTaskMarkdown.crop()

    private val EditText.start: Int get() = selectionStart.coerceAtMost(selectionEnd)

    private val EditText.end: Int get() = selectionStart.coerceAtLeast(selectionEnd)

    private fun EditText.crop(): Crop {
        val start = start
        val end = end
        return Crop(text.substring(start, end), start, end)
    }

    private fun EditText.replace(replacement: String) {
        text.replace(start, end, replacement)
    }

    private fun replaceAndSelect(wrapped: String, selection: Int) {
        with(binding.edtTaskMarkdown) {
            requestFocus()
            replace(wrapped)
            setSelection(selection)
        }
    }

    private fun replaceAndSelect(wrapped: String, selection: IntRange) {
        with(binding.edtTaskMarkdown) {
            requestFocus()
            replace(wrapped)
            setSelection(selection.first, selection.last)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _tableBinding = null
    }

    override fun onCellChanged(cell: Cell) {
        tableBinding.txtCell.text = getString(R.string.table_counter, cell.ratioX, cell.ratioY)
    }

    companion object {
        private const val TAG = "DetailFragment"
    }
}