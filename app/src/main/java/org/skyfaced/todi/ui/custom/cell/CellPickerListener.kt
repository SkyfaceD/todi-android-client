package org.skyfaced.todi.ui.custom.cell

import org.skyfaced.todi.models.cell.Cell

interface CellPickerListener {
    fun onCellChanged(cell: Cell)
}