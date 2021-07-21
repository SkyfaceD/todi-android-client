package org.skyfaced.todi.ui.signIn

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSignInBinding
import org.skyfaced.todi.utils.MIN_PASSWORD_LENGTH
import org.skyfaced.todi.utils.MIN_USERNAME_LENGTH
import org.skyfaced.todi.utils.extensions.setPercentByOrientation

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by inject()
    private val args: SignInFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupButton()
            setupEditText()
            setupObserver()

            configureGuideline()
        }
    }

    private fun FragmentSignInBinding.setupButton() {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        btnSignIn.setOnClickListener {
            val username = edtUsername.text?.toString() ?: ""
            val password = edtPassword.text?.toString() ?: ""
            val fields = listOf(username to tilUsername, password to tilPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.all { !it }) {
                if (username.length < MIN_USERNAME_LENGTH) tilUsername.error =
                    getString(R.string.error_username_length, MIN_USERNAME_LENGTH)

                if (password.length < MIN_PASSWORD_LENGTH) tilPassword.error =
                    getString(R.string.error_password_length, MIN_PASSWORD_LENGTH)
            }

            val tils = listOf(tilUsername, tilPassword)
            if (tils.any { it.isErrorEnabled }) {
                tils.filter { it.isErrorEnabled }.forEach { it.startAnimation(shakeAnimation) }
                return@setOnClickListener
            }

            if (!viewModel.validateCredentials(username, password)) {
                tils.forEach { til ->
                    til.error = getString(R.string.error_incorrect_credentials)
                }
                return@setOnClickListener
            }

            viewModel.saveIdByUsername(username)

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

    private fun FragmentSignInBinding.setupEditText() {
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

    private fun FragmentSignInBinding.configureGuideline() {
        val orientation = requireActivity().resources.configuration.orientation
        glDivider.setPercentByOrientation(orientation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.glDivider.setPercentByOrientation(newConfig.orientation)
    }

    private fun FragmentSignInBinding.setupObserver() {
        val userId = args.userId
        if (userId != null) {
            viewModel.findUserById(userId)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.user.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.STARTED
                ).collect { user ->
                    edtUsername.setText(user.username)
                    edtPassword.requestFocus()
                }
            }
        }
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}