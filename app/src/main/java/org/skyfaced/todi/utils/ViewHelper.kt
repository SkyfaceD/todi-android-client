package org.skyfaced.todi.utils

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.IdRes
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import org.skyfaced.todi.utils.enums.CornerFamily
import org.skyfaced.todi.utils.enums.CornerSide
import org.skyfaced.todi.utils.extensions.dpToPx

//@Deprecated("Awful solution", level = DeprecationLevel.WARNING)
//class CustomProperty<T : Any> : ReadWriteProperty<ViewHelper, T> {
//    @OptIn(ExperimentalStdlibApi::class)
//    @Suppress("UNCHECKED_CAST")
//    override fun getValue(thisRef: ViewHelper, property: KProperty<*>): T {
//        val key = SP_NAME + property.name
//        var value = thisRef.preferences.all[key]
//        if (property.returnType.isSubtypeOf(typeOf<CornerFamily>())) {
//            value = CornerFamily.valueOf(value as? String ?: "ROUND")
//        }
//        return value as? T ?: "#FF484848" as T
//    }
//
//    override fun setValue(thisRef: ViewHelper, property: KProperty<*>, value: T) {
//        thisRef.preferences.edit { put(property.name, value) }
//        if (thisRef.autoInvalidate) thisRef.invalidate()
//    }
//}
//
//fun <T: Any> customProperty() = CustomProperty<T>()
//
//@Suppress("NAME_SHADOWING")
//fun <T> SharedPreferences.Editor.put(key: String, value: T) {
//    val key = SP_NAME + key
//    when (value) {
//        is CornerFamily -> putString(key, value.name)
//        is String -> putString(key, value)
//        is Float -> putFloat(key, value)
//    }
//}

@Deprecated("Too bad", level = DeprecationLevel.WARNING)
class ViewHelper(
    val autoInvalidate: Boolean = false,
    val preferences: SharedPreferences
) {
    var cornerFamily: CornerFamily = preferences.getString(SP_CORNER_FAMILY, null)?.let {
        CornerFamily.valueOf(it)
    } ?: CornerFamily.ROUND
        set(value) {
            field = value
            if (autoInvalidate) invalidate()
        }
    var cornerSize: Float = preferences.getFloat(SP_CORNER_SIZE, 8.0f)
        set(value) {
            field = value
            if (autoInvalidate) invalidate()
        }
    var borderColor: String = preferences.getString(SP_BORDER_COLOR, null) ?: "#FF484848"
        set(value) {
            field = value
            if (autoInvalidate) invalidate()
        }
    var borderSize: Float = preferences.getFloat(SP_BORDER_SIZE, 2.0f)
        set(value) {
            field = value
            if (autoInvalidate) invalidate()
        }

    private val views = hashMapOf<Int, Pair<View, CornerSide>>()

    fun applyCorners(vararg pairs: Pair<View, CornerSide>) {
        pairs.forEach(::applyCorners)
    }

    fun applyCorners(pair: Pair<View, CornerSide>) {
        val view = pair.first
        views[view.id] = pair
    }

    fun invalidate() {
        for (map in views) {
            invalidate(map.key)
        }
    }

    @Suppress("UsePropertyAccessSyntax")
    fun invalidate(@IdRes viewId: Int) {
        val pair = views[viewId]
            ?: throw IllegalArgumentException("Can't extract pair for passed id $viewId")
        val view = pair.first
        val cornerSide = pair.second

        val shapeAppearanceModel = when (cornerFamily) {
            CornerFamily.SQUARE -> ShapeAppearanceModel.builder().build()
            CornerFamily.ROUND, CornerFamily.CUT -> {
                ShapeAppearanceModel.builder().apply {
                    when (cornerSide) {
                        CornerSide.All -> setAllCorners(cornerFamily.ordinal, cornerSize)
                        CornerSide.Left -> {
                            setTopLeftCorner(cornerFamily.ordinal, cornerSize)
                            setBottomLeftCorner(cornerFamily.ordinal, cornerSize)
                        }
                        CornerSide.Right -> {
                            setTopRightCorner(cornerFamily.ordinal, cornerSize)
                            setBottomRightCorner(cornerFamily.ordinal, cornerSize)
                        }
                        CornerSide.Top -> {
                            setTopLeftCorner(cornerFamily.ordinal, cornerSize)
                            setTopRightCorner(cornerFamily.ordinal, cornerSize)
                        }
                        CornerSide.Bottom -> {
                            setBottomLeftCorner(cornerFamily.ordinal, cornerSize)
                            setBottomRightCorner(cornerFamily.ordinal, cornerSize)
                        }
                    }
                }.build()
            }
        }

        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            setFillColor(view.colorStateList)
            setStroke(borderSize.dpToPx(view.context), Color.parseColor(borderColor))
            setElevation(0f)
        }

        ViewCompat.setBackground(view, shapeDrawable)
    }

    fun applyCornersAndInvalidate(vararg pairs: Pair<View, CornerSide>) {
        pairs.forEach(::applyCorners)
        invalidate()
    }

    fun save() {
        preferences.edit {
            putString(SP_CORNER_FAMILY, cornerFamily.name)
            putFloat(SP_CORNER_SIZE, cornerSize)
            putFloat(SP_BORDER_SIZE, borderSize)
            putString(SP_BORDER_COLOR, borderColor)
        }
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private val View.colorStateList: ColorStateList
        get() {
            when (this) {
                is MaterialCardView -> return cardBackgroundColor
                is MaterialButton -> return backgroundTintList ?: ColorStateList.valueOf(Color.RED)
            }

            val background = background
            return when (background) {
                is ColorDrawable -> ColorStateList.valueOf(background.color)
                else -> throw IllegalArgumentException("Can't extract color state list from $this")
            }
        }
}
