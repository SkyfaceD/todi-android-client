package org.skyfaced.todi.models.cell

import org.skyfaced.todi.ui.custom.cell.CellPickerView

data class Cell(
    val x: Int,
    val y: Int
) {
    val screenX: Float = x * (CellPickerView.CELL_WIDTH + CellPickerView.CELL_GAP)
    val screenY: Float = y * (CellPickerView.CELL_HEIGHT + CellPickerView.CELL_GAP)
    val midX: Float =
        screenX + (CellPickerView.CELL_BOUNDS.width() / 2) + CellPickerView.VIEW_MARGIN
    val midY: Float =
        screenY + (CellPickerView.CELL_BOUNDS.height() / 2) + CellPickerView.VIEW_MARGIN
    var color: Int = CellPickerView.INACTIVE_COLOR

    val ratioX: Int = x.inc()
    val ratioY: Int = y.inc()

    override fun toString(): String {
        return "Cell(x = $x, y = $y, screenX = $screenX, screenY = $screenY, midX = $midX, $midY = $midY)"
    }
}
