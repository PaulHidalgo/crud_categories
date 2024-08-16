package com.phidalgo.crudcategories.model.repository

import com.phidalgo.crudcategories.data.network.pokemon.PokemonService
import com.phidalgo.crudcategories.model.Category
import com.phidalgo.crudcategories.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(private val apiService: PokemonService) {

    private var categories = mutableListOf<Category>()

    // Función para consumir la API y obtener las categorías de Pokémon usando Retrofit
    suspend fun loadCategoriesFromApi(): List<Category> = withContext(Dispatchers.IO) {
        val pokemonListResponse = apiService.getPokemonList()

        pokemonListResponse.results.forEach { result ->
            val pokemonId = result.url.split("/".toRegex()).dropLast(1).last().toInt()
            val detailResponse = apiService.getPokemonDetail(pokemonId)

            val pokemon = Pokemon(
                id = detailResponse.id,
                name = detailResponse.name,
                imageUrl = detailResponse.sprites.front_default,
                category = detailResponse.types.first().type.name
            )

            val category = categories.find { it.name == pokemon.category }
            if (category != null) {
                category.pokemonList.add(pokemon)
            } else {
                categories.add(Category(id = categories.size + 1, name = pokemon.category, pokemonList = mutableListOf(pokemon)))
            }
        }

        categories
    }

    fun getCategories() = categories

    fun addCategory(category: Category) {
        categories.add(category)
    }

    fun updateCategory(updatedCategory: Category) {
        // Busca el índice de la categoría que deseas actualizar
        val categoryIndex = categories.indexOfFirst { it.id == updatedCategory.id }

        // Si se encuentra la categoría, se actualiza
        if (categoryIndex != -1) {
            categories[categoryIndex] = updatedCategory
        }

    }

    fun removeCategory(category: Category) {
        categories.remove(category)
    }

    fun removeAllCategory() {
        categories = mutableListOf()
    }
}
