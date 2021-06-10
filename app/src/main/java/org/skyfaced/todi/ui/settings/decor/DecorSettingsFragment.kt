package org.skyfaced.todi.ui.settings.decor

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorListener
import org.koin.android.ext.android.inject
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentDecorSettingsBinding
import org.skyfaced.todi.utils.ViewHelper
import org.skyfaced.todi.utils.enums.CornerFamily
import org.skyfaced.todi.utils.enums.CornerSide
import org.skyfaced.todi.utils.extensions.hexCodeWithReshetochkots

class DecorSettingsFragment : Fragment(R.layout.fragment_decor_settings) {
    private val binding by viewBinding(FragmentDecorSettingsBinding::bind)
    private val preferences: SharedPreferences by inject()
    private val viewHelper = ViewHelper(true, preferences)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tgCorner.addOnButtonCheckedListener { _, checkedId, _ ->
                viewHelper.cornerFamily = CornerFamily.fromId(checkedId)
            }
            tgCorner.check(viewHelper.cornerFamily.resId)

            slider.addOnChangeListener { _, value, _ ->
                viewHelper.cornerSize = value
                txtSlider.text = "${value.toInt()}dp"
            }
            slider.value = viewHelper.cornerSize

            cardColor.setOnClickListener {
                val visibility = !colorPicker.isVisible
                colorPicker.isVisible = visibility
                colorAlphaSlide.isVisible = visibility
                colorBrightnessSlide.isVisible = visibility
            }

            colorPicker.attachAlphaSlider(colorAlphaSlide)
            colorPicker.attachBrightnessSlider(colorBrightnessSlide)
            colorPicker.setInitialColor(Color.parseColor(viewHelper.borderColor))
            colorPicker.setColorListener(ColorListener { color, _ ->
                val borderColor = ColorEnvelope(color)
                viewHelper.borderColor = borderColor.hexCodeWithReshetochkots
                imgColor.setBackgroundColor(borderColor.color)
                txtColor.text = viewHelper.borderColor
            })

            btnSave.setOnClickListener {
                viewHelper.save()
            }

            viewHelper.applyCorners(
                btnSave to CornerSide.All,
                btnCornerRound to CornerSide.Left,
                btnCornerCut to CornerSide.None,
                btnCornerSquare to CornerSide.Right,
                cardPreview to CornerSide.All,
                cardSlider to CornerSide.All,
                cardColor to CornerSide.All
            )
        }
    }
}
