package com.phidalgo.crudcategories.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.phidalgo.crudcategories.R
import com.phidalgo.crudcategories.databinding.DialogCategoryBinding
import com.phidalgo.crudcategories.model.Category
import com.phidalgo.crudcategories.ui.home.adapter.CategoryAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// Diálogo para crear o editar una categoría
class CategoryDialogFragment : DialogFragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private var _binding: DialogCategoryBinding? = null
    private val binding get() = _binding!!
    private var categoryToEdit: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryToEdit?.let { category ->
            binding.editTextCategoryName.setText(category.name)
            binding.buttonAddCategory.text = "Update"
        }

        binding.buttonAddCategory.setOnClickListener {
            val categoryName = binding.editTextCategoryName.text.toString()
            if (categoryName.isNotEmpty()) {
                if (categoryToEdit != null) {
                    // Actualizar categoría existente
                    categoryToEdit?.name = categoryName
                    //viewModel.loadCategories() // Recargar la lista con los cambios
                } else {
                    // Crear nueva categoría
                    val newCategory = Category(
                        id = generateCategoryId(),
                        name = categoryName,
                        pokemonList = mutableListOf()
                    )
                    viewModel.addCategory(newCategory)
                }
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateCategoryId(): Int {
        return (Math.random() * 100000).toInt()
    }

    companion object {
        // Método para instanciar el diálogo con una categoría existente para editar
        fun newInstance(category: Category): CategoryDialogFragment {
            val fragment = CategoryDialogFragment()
            fragment.categoryToEdit = category
            return fragment
        }
    }
}
