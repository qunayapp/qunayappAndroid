package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ItemPetBinding
import com.pe.mascotapp.vistas.entities.PetEntity

class PetAdapter(private val pets: List<PetEntity>) :
    RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(petEntity: PetEntity) {
            binding.petEntity = petEntity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPetBinding.inflate(layoutInflater, parent, false)
        return PetViewHolder(binding)
    }

    override fun getItemCount(): Int = pets.size

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
        holder.itemView.setOnClickListener {
            pet.isSelected = !pet.isSelected
            notifyItemChanged(position)
        }
    }
}