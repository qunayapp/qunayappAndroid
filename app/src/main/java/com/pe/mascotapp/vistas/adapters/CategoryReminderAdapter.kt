package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ItemCategoryReminderBinding
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity

class CategoryReminderAdapter(
    private val categories: List<CategoryReminderEntity>,
) :
    RecyclerView.Adapter<CategoryReminderAdapter.CategoryReminderViewHolder>() {
    private var positionSelected = 0

    class CategoryReminderViewHolder(private val binding: ItemCategoryReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryReminderEntity: CategoryReminderEntity) {
            binding.categoryReminder = categoryReminderEntity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryReminderBinding.inflate(layoutInflater, parent, false)
        return CategoryReminderViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryReminderViewHolder, position: Int) {
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            if (position != positionSelected) {
                categories[position].isSelected = true
                categories[positionSelected].isSelected = false
                notifyItemChanged(positionSelected)
                positionSelected = position
                notifyItemChanged(position)
            }
        }
    }
}