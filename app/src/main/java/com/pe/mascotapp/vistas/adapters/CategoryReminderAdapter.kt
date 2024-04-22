package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ItemCategoryReminderBinding
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity

class CategoryReminderAdapter(
    private val categories: List<CategoryReminderEntity> = listOf(),
    val itemOnClick: () -> Unit = {},
) : RecyclerView.Adapter<CategoryReminderAdapter.CategoryReminderViewHolder>() {
    private var positionSelected = -1

    class CategoryReminderViewHolder(private val binding: ItemCategoryReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryReminderEntity: CategoryReminderEntity) {
            binding.categoryReminder = categoryReminderEntity
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryReminderBinding.inflate(layoutInflater, parent, false)
        return CategoryReminderViewHolder(binding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(
        holder: CategoryReminderViewHolder,
        position: Int,
    ) {
        if (categories[position].isSelected) positionSelected = position
        holder.bind(categories[position])
        holder.itemView.setOnClickListener {
            if (position != positionSelected) {
                val tempPosition = positionSelected
                positionSelected = position
                categories[positionSelected].isSelected = true
                notifyItemChanged(positionSelected)
                if (tempPosition != -1) {
                    categories[tempPosition].isSelected = false
                    notifyItemChanged(tempPosition)
                }
            }
            itemOnClick()
        }
    }
}
