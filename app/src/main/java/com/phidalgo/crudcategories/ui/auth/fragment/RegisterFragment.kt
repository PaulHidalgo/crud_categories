// RegisterFragment.kt
package com.phidalgo.crudcategories.ui.auth.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.databinding.FragmentRegisterBinding
import com.phidalgo.crudcategories.ui.auth.AuthViewModel
import com.phidalgo.crudcategories.util.KeyboardUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val email = binding.includeFormEmailPsw.emailEditText.text.toString()
            val password = binding.includeFormEmailPsw.passwordEditText.text.toString()

            var isValid = true

            if (email.isEmpty()) {
                binding.includeFormEmailPsw.emailInputLayout.error = "Email is required."
                isValid = false
            } else {
                binding.includeFormEmailPsw.emailInputLayout.error = null
            }

            if (password.isEmpty()) {
                binding.includeFormEmailPsw.passwordInputLayout.error = "Password is required."
                isValid = false
            } else {
                binding.includeFormEmailPsw.passwordInputLayout.error = null
            }

            if (isValid) {
                // Start the loading animation and blur effect
                showLoading(true)

                // Call the authentication service
                authViewModel.register(email, password)
            }
        }

        authViewModel.registerResult.observe(viewLifecycleOwner) { authResult ->
            KeyboardUtils.hideKeyboard(requireActivity(),view)
            showLoading(false)

            if (authResult != null) {
                showErrorDialog(
                    "Register successful",
                    "Register success. Click in OK to go to login page.",
                    positiveButtonClick = {
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    })
            } else {
                showErrorDialog("Error", "Register failed. Please try again.")
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Stop the loading animation and blur effect
            showLoading(false)
            KeyboardUtils.hideKeyboard(requireActivity(),view)
            showErrorDialog("Error", errorMessage)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.includeLoading.blurOverlay.visibility = View.VISIBLE
            binding.includeLoading.loadingAnimationView.visibility = View.VISIBLE
            binding.includeLoading.loadingAnimationView.playAnimation()
        } else {
            binding.includeLoading.blurOverlay.visibility = View.GONE
            binding.includeLoading.loadingAnimationView.cancelAnimation()
            binding.includeLoading.loadingAnimationView.visibility = View.GONE
        }
    }

    private fun showErrorDialog(title:String,
        message: String,
        positiveButtonClick: ((dialog: AlertDialog) -> Unit)? = null
    ) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialogInterface, _ ->
                val dialog = dialogInterface as AlertDialog
                positiveButtonClick?.invoke(dialog) ?: dialog.dismiss()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

}
