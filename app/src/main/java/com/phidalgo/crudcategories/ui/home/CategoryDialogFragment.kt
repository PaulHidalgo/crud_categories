package com.phidalgo.crudcategories.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
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

        categoryToEdit = arguments?.getParcelable("category")

        categoryToEdit?.let { category ->
            binding.editTextCategoryName.setText(category.name)
            binding.buttonAddCategory.text = getString(R.string.update)
        }

        binding.buttonAddCategory.setOnClickListener {
            val categoryName = binding.editTextCategoryName.text.toString()
            val email = binding.editTextCategoryName.text.toString()
            var isValid = true
            if (email.isEmpty()) {
                binding.categoryInputLayout.error = getString(R.string.category_name)
                isValid = false
            } else {
                binding.categoryInputLayout.error = null
            }
            if (isValid) {
                if (categoryName.isNotEmpty()) {
                    if (categoryToEdit != null) {
                        // Actualizar categoría existente
                        categoryToEdit?.name = categoryName
                        viewModel.updateCategory(categoryToEdit!!)
                        viewModel.loadCategories()
                    } else {
                        // Crear nueva categoría
                        val newCategory = Category(
                            id = generateCategoryId(),
                            name = categoryName,
                            pokemonList = mutableListOf()
                        )
                        viewModel.addCategory(newCategory)
                    }

                    setFragmentResult("categoryUpdate", Bundle())

                    dismiss()
                }
            }
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateCategoryId(): Int {
        return (Math.random() * 100000).toInt()
    }

}
