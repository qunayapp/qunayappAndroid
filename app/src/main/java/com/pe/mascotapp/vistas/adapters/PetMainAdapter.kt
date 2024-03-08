package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ItemPetFrontBinding
import com.pe.mascotapp.vistas.entities.PetEntity

class PetMainAdapter(val pets: List<PetEntity> = listOf(), val itemOnClick: () -> Unit = {}) : RecyclerView.Adapter<PetMainAdapter.PetMainViewHolder>(){

    class PetMainViewHolder(private val binding: ItemPetFrontBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(petEntity: PetEntity) {
            binding.petEntity = petEntity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPetFrontBinding.inflate(layoutInflater, parent, false)
        return PetMainViewHolder(binding)
    }

    override fun getItemCount(): Int =pets.size

    override fun onBindViewHolder(holder: PetMainViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
        holder.itemView.setOnClickListener {
            pet.isSelected = !pet.isSelected
            itemOnClick()
            notifyItemChanged(position)
        }
    }
}