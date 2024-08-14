package com.phidalgo.crudcategories.model

data class PokemonResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)
