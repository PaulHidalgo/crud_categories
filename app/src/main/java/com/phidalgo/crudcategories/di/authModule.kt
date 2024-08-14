package com.phidalgo.crudcategories.di

import com.phidalgo.crudcategories.data.network.auth.AuthRepository
import com.phidalgo.crudcategories.ui.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthRepository(get(), androidContext()) } // Provide AuthRepository, assuming it has other dependencies
    viewModel { AuthViewModel(get()) } // Provide AuthViewModel with AuthRepository
}
