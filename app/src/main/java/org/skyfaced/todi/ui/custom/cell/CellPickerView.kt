package org.skyfaced.todi.ui.custom.cell

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import org.skyfaced.todi.utils.extensions.lazySafetyNone
import kotlin.math.pow
import kotlin.math.sqrt

//TODO Theming; Customization via XML;
class CellPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val isDebug: Boolean = false
) : View(context, attrs, defStyleAttr) {
    private var _canvas: Canvas? = null
    private val canvas = checkNotNull(_canvas) { "Canvas is null" }

    private val paint = Paint()
    private val textPaint = Paint()
    private val backgroundPaint = Paint()

    var cellPickerListener: CellPickerListener? = null

    private val cells = arrayListOf<Cell>()
    private var cell: Cell? = null
        set(value) {
            field = value
            changeCellColor()
            cellPickerListener?.onCellChanged(value ?: cells[0])
        }

    val currentCell get() = checkNotNull(cell) { "Cell is null, magic" }

    fun resetCell() {
        cell = cells[0]
    }

    init {
        paint.apply {
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            strokeWidth = STROKE_WIDTH
        }

        textPaint.apply {
            isAntiAlias = true
            color = Color.parseColor("#000000")
            textAlign = Paint.Align.CENTER
            textSize = 48f
        }

        backgroundPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.RED
        }

        for (i in 0 until CELL_ROWS) {
            for (j in 0 until CELL_COLUMNS) {
                cells.add(Cell(j, i))
            }
        }

        cell = cells[0]
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //Same calculations but more clarified
        //(CELL_COLUMNS - 1) * CELL_GAP + CELL_COLUMNS * CELL_WIDTH + VIEW_MARGIN * 2
        //(CELL_ROWS - 1) * CELL_GAP + CELL_ROWS * CELL_WIDTH + VIEW_MARGIN * 2
        val width = (CELL_COLUMNS * (CELL_HEIGHT + CELL_GAP) - CELL_GAP + VIEW_MARGIN * 2).toInt()
        val height = (CELL_ROWS * (CELL_WIDTH + CELL_GAP) - CELL_GAP + VIEW_MARGIN * 2).toInt()
        setMeasuredDimension(width, height)
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        _canvas = c

        canvas.save()
        if (isDebug) {
            val w = width.toFloat()
            val h = height.toFloat()
            Log.d(TAG, "onDraw: width = $w, height = $h")
            canvas.drawRect(0f, 0f, w, h, backgroundPaint)
        }
        canvas.translate(VIEW_MARGIN, VIEW_MARGIN)
        drawCells()
        canvas.restore()
    }

    @Suppress("UnnecessaryVariable")
    private fun drawCells() {
        cells.forEachIndexed { index, cell ->
            canvas.save()
            canvas.translate(cell.screenX, cell.screenY)

            paint.style = Paint.Style.FILL
            paint.color = cell.color
            canvas.drawRoundRect(CELL_BOUNDS, CELL_CORNER_RADIUS, CELL_CORNER_RADIUS, paint)

            paint.style = Paint.Style.STROKE
            paint.color = STROKE_COLOR
            canvas.drawRoundRect(CELL_BOUNDS, CELL_CORNER_RADIUS, CELL_CORNER_RADIUS, paint)

            canvas.restore()

            if (isDebug) {
                Log.d(TAG, "drawCells: $cell")
                canvas.drawText("$index", cell.midX, cell.midY, textPaint)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                cell = findClosestCell(event.x, event.y)
            }
        }

        return true
    }

    private fun findClosestCell(x: Float, y: Float): Cell {
        var cell = cells[0]
        var distance = euclideanDistance(cell.midX, cell.midY, x, y)
        var temporaryDistance: Float

        cells.forEach { c ->
            temporaryDistance = euclideanDistance(c.midX, c.midY, x, y)
            if (temporaryDistance < distance) {
                cell = c
                distance = temporaryDistance
            }
        }

        return cell
    }

    private fun euclideanDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x2 - x1).pow(2) + (y2 - y1).pow(2))
    }

    private fun changeCellColor() {
        for (i in 0 until CELL_ROWS) {
            for (j in 0 until CELL_COLUMNS) {
                cells[(i * CELL_COLUMNS) + j].color =
                    if (i <= cell!!.y && j <= cell!!.x) ACTIVE_COLOR else INACTIVE_COLOR
            }
        }

        invalidate()
    }

    companion object {
        const val TAG = "CellPicker"

        const val CELL_ROWS = 3
        const val CELL_COLUMNS = 3
        const val CELL_WIDTH = 150f
        const val CELL_HEIGHT = 140f
        const val CELL_GAP = 16f
        const val CELL_CORNER_RADIUS = CELL_WIDTH / 6
        val CELL_BOUNDS by lazySafetyNone { RectF(0f, 0f, CELL_WIDTH, CELL_HEIGHT) }

        val INACTIVE_COLOR by lazySafetyNone { Color.parseColor("#E0E0E0") }
        val ACTIVE_COLOR by lazySafetyNone { Color.parseColor("#AB47BC") }
        val STROKE_COLOR by lazySafetyNone { Color.parseColor("#66000000") }

        const val VIEW_MARGIN = 44f
        const val STROKE_WIDTH = 8f
    }
}