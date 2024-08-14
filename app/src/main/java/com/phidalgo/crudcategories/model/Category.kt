package com.phidalgo.crudcategories.model

data class Category(
    val id: Int,
    var name: String,
    val pokemonList: MutableList<Pokemon>
)
