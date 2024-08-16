package com.phidalgo.crudcategories.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phidalgo.crudcategories.databinding.ItemCategoryBinding
import com.phidalgo.crudcategories.model.Category

class CategoryAdapter(
    private var categories: List<Category>,
    private val onEditClick: (Category) -> Unit,
    private val onDeleteClick: (Category) -> Unit,
    private val onCategoryClick: (Category) -> Unit  // Callback para clic en la categoría
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            val formattedText = category.name.lowercase().replaceFirstChar { it.uppercase() }
            binding.categoryName.text = formattedText

            // Configurar el botón de editar
            binding.buttonEdit.setOnClickListener {
                onEditClick(category)
            }

            // Configurar el botón de eliminar
            binding.buttonDelete.setOnClickListener {
                onDeleteClick(category)
            }

            // Configurar el clic en la categoría
            binding.root.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}
