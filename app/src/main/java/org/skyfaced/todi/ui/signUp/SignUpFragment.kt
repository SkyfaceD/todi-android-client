package org.skyfaced.todi.ui.signUp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSignUpBinding
import org.skyfaced.todi.utils.MIN_PASSWORD_LENGTH
import org.skyfaced.todi.utils.MIN_USERNAME_LENGTH
import org.skyfaced.todi.utils.extensions.setPercentByOrientation

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupButton()
            setupEditText()
            setupObserver()

            configureGuideline()
        }
    }

    private fun FragmentSignUpBinding.setupButton() {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        btnNext.setOnClickListener {
            val username = edtUsername.text?.toString()

            if (username.isNullOrEmpty()) {
                tilUsername.error = getString(R.string.error_fill_empty_field)
                tilUsername.startAnimation(shakeAnimation)
                return@setOnClickListener
            }

            if (username.length < MIN_USERNAME_LENGTH) {
                tilUsername.error = getString(R.string.error_username_length, MIN_USERNAME_LENGTH)
                tilUsername.startAnimation(shakeAnimation)
                return@setOnClickListener
            }

            //FIXME
            if (runBlocking { viewModel.isUsernameFree(username) }) {
                tilUsername.error = getString(R.string.error_username_already_taken)
                return@setOnClickListener
            }

            viewModel.toggleGroup()
            edtPassword.requestFocus()
        }

        //TODO update validation
        btnSignUp.setOnClickListener {
            val password = edtPassword.text?.toString()
            val confirmPassword = edtConfirmPassword.text?.toString()
            val fields = listOf(password to tilPassword, confirmPassword to tilConfirmPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.any { !it }) {
                if (password!!.length < MIN_PASSWORD_LENGTH) tilPassword.error =
                    getString(R.string.error_password_length, MIN_PASSWORD_LENGTH)
            }

            val list = listOf(tilPassword, tilConfirmPassword)
            if (list.any { it.isErrorEnabled }) {
                list.filter { it.isErrorEnabled }.forEach { it.startAnimation(shakeAnimation) }
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                tilConfirmPassword.error = getString(R.string.error_confirm_password)
                tilConfirmPassword.startAnimation(shakeAnimation)
                return@setOnClickListener
            }

            //FIXME
            viewModel.registerUser(
                edtUsername.text?.toString() ?: "",
                edtPassword.text?.toString() ?: ""
            )
        }
    }

    private fun showErrorOnNullOrEmptyField(pair: Pair<String?, TextInputLayout>): Boolean {
        when (pair.first.isNullOrEmpty()) {
            true -> pair.second.error = getString(R.string.error_fill_empty_field)
            false -> pair.second.isErrorEnabled = false
        }

        return pair.second.isErrorEnabled
    }

    private fun FragmentSignUpBinding.setupEditText() {
        listOf(
            edtUsername to tilUsername,
            edtPassword to tilPassword,
            edtConfirmPassword to tilConfirmPassword
        ).forEach { pair ->
            pair.first.doAfterTextChanged {
                if (!pair.second.isErrorEnabled) return@doAfterTextChanged
                pair.second.isErrorEnabled = false
            }
        }
    }

    private fun FragmentSignUpBinding.setupObserver() {
        viewModel.visibleGroup.observe(viewLifecycleOwner) { state ->
            grpUsername.isVisible = state
            grpPassword.isVisible = !state
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registration.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect { user ->
                Log.d(TAG, "$user")
                val direction =
                    SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(user.id)
                findNavController().navigate(direction)
            }
        }
    }

    private fun FragmentSignUpBinding.configureGuideline() {
        val orientation = requireActivity().resources.configuration.orientation
        glDivider.setPercentByOrientation(orientation)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            with(binding) {
                when {
                    grpUsername.isVisible -> {
                        tilUsername.clearAnimation()

                        findNavController().navigateUp()
                    }
                    grpPassword.isVisible -> {
                        tilPassword.isErrorEnabled = false
                        tilConfirmPassword.isErrorEnabled = false
                        tilPassword.clearAnimation()
                        tilConfirmPassword.clearAnimation()

                        viewModel.toggleGroup()
                    }
                }
            }
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.glDivider.setPercentByOrientation(newConfig.orientation)
    }

    companion object {
        private const val TAG = "SignUpFragment"
    }
}