package com.phidalgo.crudcategories.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.data.network.auth.AuthRepository
import com.phidalgo.crudcategories.databinding.ActivityAuthBinding
import com.phidalgo.crudcategories.ui.main.MainActivity
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authRepository.isSessionActive()) {
            navigateToMainActivity()
            return
        }

        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Get the NavHostFragment from the FragmentContainerView
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.authNavHostFragment.id) as NavHostFragment

        // Get the NavController from the NavHostFragment
        val navController =  navHostFragment.navController

        // Now that we have the NavController, we can safely set up the ActionBar
        setupActionBarWithNavController(navController)
    }

    // Optional: Handle Up navigation
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.authNavHostFragment.id) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
