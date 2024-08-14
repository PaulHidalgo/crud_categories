package com.phidalgo.crudcategories.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phidalgo.crudcategories.model.Category
import com.phidalgo.crudcategories.model.Pokemon
import com.phidalgo.crudcategories.model.repository.CategoryRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Función para cargar las categorías desde la API
    fun loadCategoriesFromApi() {
        _isLoading.value = true
        viewModelScope.launch {
            val loadedCategories = categoryRepository.loadCategoriesFromApi()
            _categories.value = loadedCategories
            _isLoading.value = false
        }
    }

    fun getPokemonByCategory(categoryName: String): List<Pokemon> {
        // Normalize the category name to prevent mismatches due to case sensitivity or extra spaces
        val normalizedCategoryName = categoryName.trim().lowercase()
        loadCategories()
        // Check if _categories contains data
        val categories = _categories.value
        if (categories.isNullOrEmpty()) {
            return listOf(Pokemon(0, "No data available", "no_image_url","")) // Replace with a meaningful default
        }

        // Find the category by its name
        val category = categories.find { it.name.trim().lowercase() == normalizedCategoryName }

        return category?.pokemonList ?: listOf(Pokemon(0, "No pokemons in this category", "no_image_url",""))
    }


    fun loadCategories() {
        _categories.value = categoryRepository.getCategories()
    }

    fun addCategory(category: Category) {
        categoryRepository.addCategory(category)
        loadCategories()
    }

    fun removeCategory(category: Category) {
        categoryRepository.removeCategory(category)
        loadCategories()
    }
}
