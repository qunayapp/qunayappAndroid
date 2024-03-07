package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.TabAnimalBinding
import com.pe.mascotapp.vistas.entities.TabAnimalEntity

class TabAnimalAdapter(var tabAnimals: List<TabAnimalEntity>) :
    RecyclerView.Adapter<TabAnimalAdapter.TabAnimalViewHolder>() {

    private var positionSelected = 0

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
                positionSelected = position
                notifyDataSetChanged()
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
            Glide.with(binding.root.context)
                .load(animal.image)
                .placeholder(R.drawable.perro1)
                .error(R.drawable.perro1)
                .into(binding.ivTabAnimal)
            if (animal.isSelected) {
                binding.ivTabAnimal.strokeColor =
                    ContextCompat.getColorStateList(binding.root.context, R.color.white)
                binding.ivTabAnimal.strokeWidth = 2.00f
            }
            binding.tvTabName.setTextColor(ContextCompat.getColor(binding.root.context, textColor))
        }
    }
}

