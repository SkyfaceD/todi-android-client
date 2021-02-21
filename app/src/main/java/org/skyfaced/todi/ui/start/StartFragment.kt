package org.skyfaced.todi.ui.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {
    private val binding by viewBinding(FragmentStartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setButtons()
            setTextViews()
        }
    }

    private fun FragmentStartBinding.setTextViews() {
        txtContinueAsGuest.setOnClickListener {
            val direction = StartFragmentDirections.actionStartFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

    private fun FragmentStartBinding.setButtons() {
        btnSignIn.setOnClickListener {
            val direction = StartFragmentDirections.actionStartFragmentToSignInFragment()
            findNavController().navigate(direction)
        }

        btnSignUp.setOnClickListener {
            val direction = StartFragmentDirections.actionStartFragmentToSignUpFragment()
            findNavController().navigate(direction)
        }
    }
}