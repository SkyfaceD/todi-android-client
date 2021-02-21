package org.skyfaced.todi.ui.signIn

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSignInBinding
import org.skyfaced.todi.utils.extensions.setGuideline

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            glDivider.setGuideline(requireActivity().resources.configuration.orientation)
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.glDivider.setGuideline(newConfig.orientation)
    }
}