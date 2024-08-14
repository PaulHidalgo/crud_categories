package com.phidalgo.crudcategories.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phidalgo.crudcategories.databinding.FragmentHomeBinding
import com.phidalgo.crudcategories.model.Category
import com.phidalgo.crudcategories.ui.home.adapter.CategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: CategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         adapter = CategoryAdapter(
            emptyList(),
            onEditClick = { category -> showEditCategoryDialog(category) },
            onDeleteClick = { category -> viewModel.removeCategory(category) },
            onCategoryClick = { category -> navigateToPokemonList(category) }
        )
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            binding.progressBar.visibility = View.GONE
            adapter!!.updateCategories(categories)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.fabAddCategory.setOnClickListener {
            CategoryDialogFragment().show(childFragmentManager, "CategoryDialog")
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.loadCategories()
        }

        // Cargar las categorías desde la API cuando el fragmento es creado
        viewModel.loadCategoriesFromApi()
    }

    private fun showEditCategoryDialog(category: Category) {
        val dialogFragment = CategoryDialogFragment.newInstance(category)
        dialogFragment.show(childFragmentManager, "EditCategoryDialog")
    }

    private fun navigateToPokemonList(category: Category) {
        val action = HomeFragmentDirections.actionHomeFragmentToPokemonListFragment(category.name)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
