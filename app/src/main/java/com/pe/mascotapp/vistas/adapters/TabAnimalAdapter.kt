package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.TabAnimalBinding

class TabAnimalAdapter(private val tabAnimals: List<TabAnimalEntity>) :
    RecyclerView.Adapter<TabAnimalAdapter.TabAnimalViewHolder>() {

    var positionSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabAnimalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TabAnimalBinding.inflate(layoutInflater, parent, false)
        return TabAnimalViewHolder(binding)
    }

    override fun getItemCount(): Int = tabAnimals.size

    override fun onBindViewHolder(holder: TabAnimalViewHolder, position: Int) {
        val animal = tabAnimals[position]
        holder.bind(animal)
        holder.itemView.setOnClickListener {
            if (position != positionSelected) {
                tabAnimals[positionSelected].isSelected = false
                animal.isSelected = true
                notifyItemChanged(position)
                notifyItemChanged(positionSelected)
                positionSelected = position
            }
        }
    }

    class TabAnimalViewHolder(private val binding: TabAnimalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: TabAnimalEntity) {
            binding.tabAnimal = animal
            val background =
                if (animal.isSelected) R.drawable.ic_tab_selected else R.drawable.ic_tab_normal
            val textColor = if (animal.isSelected) R.color.white else R.color.third
            binding.llTab.background = ResourcesCompat.getDrawable(
                binding.root.resources,
                background,
                binding.root.context.theme
            )
            binding.tvTabName.setTextColor(ContextCompat.getColor(binding.root.context, textColor))
        }
    }
}


class TabAnimalEntity(
    var isSelected: Boolean,
    val name: String,
    val image: Int
)