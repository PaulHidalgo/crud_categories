package com.phidalgo.crudcategories.ui.auth.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.databinding.FragmentLoginBinding
import com.phidalgo.crudcategories.ui.auth.AuthViewModel
import com.phidalgo.crudcategories.ui.main.MainActivity
import com.phidalgo.crudcategories.util.KeyboardUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
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
                authViewModel.login(email, password)
            }
        }

        binding.registerButton.setOnClickListener {
            // Navigate to the RegisterFragment
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }

        authViewModel.loginResult.observe(viewLifecycleOwner) { authResult ->
            // Stop the loading animation and blur effect
            KeyboardUtils.hideKeyboard(requireActivity(),view)
            showLoading(false)

            if (authResult != null) {
                // Navigate to the main activity
                navigateToMainActivity()
            } else {
                showErrorDialog("Login failed. Please try again.")
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            // Stop the loading animation and blur effect
            showLoading(false)
            KeyboardUtils.hideKeyboard(requireActivity(),view)
            showErrorDialog(errorMessage)
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

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Finish the current activity to prevent back navigation to login
    }
}
