package org.skyfaced.todi.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)

    private lateinit var adapter: SettingsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
    }

//    private fun FragmentSettingsBinding.setupAdapter() {
//        adapter = SettingsAdapter { position ->
//            val direction = when (position) {
//                else -> throw IllegalArgumentException("Can't navigate to $position position")
//            }
//            findNavController().navigate(direction)
//        }
//        recyclerView.adapter = adapter
//    }
}
