package com.phidalgo.crudcategories.ui.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.phidalgo.crudcategories.databinding.FragmentPokemonListBinding
import com.phidalgo.crudcategories.ui.home.HomeViewModel
import com.phidalgo.crudcategories.ui.pokemon.adapter.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by sharedViewModel()

    private val args: PokemonListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.categoryName

        val pokemonList = viewModel.getPokemonByCategory(category)

        val adapter = PokemonAdapter(pokemonList)
        binding.recyclerViewPokemon.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPokemon.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
