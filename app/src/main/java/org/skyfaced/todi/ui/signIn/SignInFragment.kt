package org.skyfaced.todi.ui.signIn

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSignInBinding
import org.skyfaced.todi.utils.MIN_PASSWORD_LENGTH
import org.skyfaced.todi.utils.MIN_USERNAME_LENGTH
import org.skyfaced.todi.utils.extensions.setGuideline

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setButtons()
            setEditTexts()

            glDivider.setGuideline(requireActivity().resources.configuration.orientation)
        }
    }

    private fun FragmentSignInBinding.setButtons() {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        btnSignIn.setOnClickListener {
            val username = edtUsername.text?.toString()
            val password = edtPassword.text?.toString()
            val fields = listOf(username to tilUsername, password to tilPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.all { !it }) {
                if (username!!.length < MIN_USERNAME_LENGTH) tilUsername.error =
                    getString(R.string.error_username_length, MIN_USERNAME_LENGTH)

                if (password!!.length < MIN_PASSWORD_LENGTH) tilPassword.error =
                    getString(R.string.error_password_length, MIN_PASSWORD_LENGTH)
            }

            val list = listOf(tilUsername, tilPassword)
            if (list.any { it.isErrorEnabled }) {
                list.filter { it.isErrorEnabled }.forEach { it.startAnimation(shakeAnimation) }

                return@setOnClickListener
            }

            //TODO network work

            //For test purpose
            val direction = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
            findNavController().navigate(direction)
        }
    }

    private fun showErrorOnNullOrEmptyField(pair: Pair<String?, TextInputLayout>): Boolean {
        when (pair.first.isNullOrEmpty()) {
            true -> pair.second.error = getString(R.string.error_fill_empty_field)
            false -> pair.second.isErrorEnabled = false
        }

        return pair.second.isErrorEnabled
    }

    private fun FragmentSignInBinding.setEditTexts() {
        listOf(
            edtUsername to tilUsername,
            edtPassword to tilPassword,
        ).forEach { pair ->
            pair.first.doAfterTextChanged {
                if (!pair.second.isErrorEnabled) return@doAfterTextChanged
                pair.second.isErrorEnabled = false
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.glDivider.setGuideline(newConfig.orientation)
    }
}