package com.phidalgo.crudcategories.data.network.pokemon

import com.phidalgo.crudcategories.model.PokemonDetailResponse
import com.phidalgo.crudcategories.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    // Obtener la lista de Pokémon
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonResponse

    // Obtener detalles de un Pokémon específico
    @GET("pokemon/{id}/")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailResponse
}
