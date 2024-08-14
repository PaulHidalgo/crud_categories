package com.phidalgo.crudcategories.ui.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phidalgo.crudcategories.databinding.ItemPokemonBinding
import com.phidalgo.crudcategories.model.Pokemon

class PokemonAdapter(
    private var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.textPokemonName.text = pokemon.name
            Glide.with(binding.root)
                .load(pokemon.imageUrl)
                .into(binding.imagePokemon)
        }
    }
}
