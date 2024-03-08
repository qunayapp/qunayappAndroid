package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemPetBinding
import com.pe.mascotapp.vistas.entities.PetEntity

class PetAdapter(val pets: List<PetEntity> = listOf(), val itemOnClick: () -> Unit = {}) :
    RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(petEntity: PetEntity) {
            Glide.with(binding.root.context)
                .load(petEntity.image)
                .placeholder(R.drawable.perro1)
                .error(R.drawable.perro1)
                .into(binding.ivPet)
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
            itemOnClick()
            notifyItemChanged(position)
        }
    }
}