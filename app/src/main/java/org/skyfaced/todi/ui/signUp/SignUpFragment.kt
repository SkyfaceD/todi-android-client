package org.skyfaced.todi.ui.signUp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import org.skyfaced.todi.R
import org.skyfaced.todi.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setButtons()
            setEditTexts()
            setupObservers()
        }
    }

    private fun FragmentSignUpBinding.setButtons() {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        btnNext.setOnClickListener {
            val username = edtUsername.text?.toString()

            if (username.isNullOrEmpty()) {
                tilUsername.error = getString(R.string.error_fill_empty_field)
                tilUsername.startAnimation(shakeAnimation)
                return@setOnClickListener
            }

            if (username.length < 4) {
                tilUsername.error = getString(R.string.error_username_length)
                tilUsername.startAnimation(shakeAnimation)
                return@setOnClickListener
            }

            //TODO network work

            //For test purpose
            viewModel.toggleGroup()
            edtPassword.requestFocus()
        }

        //TODO update validation
        btnSignUp.setOnClickListener {
            val password = edtPassword.text?.toString()
            val confirmPassword = edtConfirmPassword.text?.toString()
            val fields = listOf(password to tilPassword, confirmPassword to tilConfirmPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.any { !it }) {
                if (password!!.length < 8) tilPassword.error =
                    getString(R.string.error_password_length)
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

            //TODO network work

            //For test purpose
            val direction = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(direction)
        }
    }

    private fun FragmentSignUpBinding.setEditTexts() {
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

    private fun FragmentSignUpBinding.setupObservers() {
        viewModel.visibleGroup.observe(viewLifecycleOwner) { state ->
            grpUsername.isVisible = state
            grpPassword.isVisible = !state
        }
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

    private fun showErrorOnNullOrEmptyField(pair: Pair<String?, TextInputLayout>): Boolean {
        when (pair.first.isNullOrEmpty()) {
            true -> pair.second.error = getString(R.string.error_fill_empty_field)
            false -> pair.second.isErrorEnabled = false
        }

        return pair.second.isErrorEnabled
    }
}