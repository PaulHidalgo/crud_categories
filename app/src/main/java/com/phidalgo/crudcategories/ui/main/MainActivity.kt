package com.phidalgo.crudcategories.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.data.network.auth.AuthRepository
import com.phidalgo.crudcategories.databinding.ActivityMainBinding
import com.phidalgo.crudcategories.databinding.NavHeaderMainBinding
import com.phidalgo.crudcategories.ui.auth.AuthActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val authRepository: AuthRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if session is active, if not navigate to login
        if (!authRepository.isSessionActive()) {
            navigateToLogin()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Inflate the nav_header_main and access its views
        val headerView = binding.navView.getHeaderView(0)
        val headerBinding = NavHeaderMainBinding.bind(headerView)

        headerBinding.email.text = authRepository.getCurrentUser()?.email ?: "correo@host.com"

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Define top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_slideshow
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Handle navigation view item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    logout()
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    // Handle other navigation items
                    NavigationView.OnNavigationItemSelectedListener { menuItem ->
                        navController.navigate(menuItem.itemId)
                        true
                    }
                    binding.drawerLayout.closeDrawers() // Close the drawer after selection
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun logout() {
        authRepository.logout()
        navigateToLogin()
    }
}
