package org.skyfaced.todi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.ActivityMainBinding
import org.skyfaced.todi.utils.enums.Theme
import org.skyfaced.todi.utils.extensions.setTheme

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Theme.LightDefault)
        super.onCreate(savedInstanceState)
    }
}