package com.phidalgo.crudcategories.di

import com.google.firebase.auth.FirebaseAuth
import com.phidalgo.crudcategories.model.repository.CategoryRepository
import com.phidalgo.crudcategories.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { FirebaseAuth.getInstance() }

    single { CategoryRepository(get()) }

    viewModel { HomeViewModel(get()) }

}
