package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ContainerOptionsAdapterBinding

class ContainerOptionsAdapter(
    private val listOptionsFieldAdapter: List<OptionFieldAdapter>,
    private val canAddOtherContainer: Boolean,
    private val addRecyclerView: () -> Unit,
) : RecyclerView.Adapter<ContainerOptionsAdapter.ContainerOptionsViewHolder>() {
    class ContainerOptionsViewHolder(private val binding: ContainerOptionsAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            optionFieldAdapter: OptionFieldAdapter,
            addRecyclerView: () -> Unit,
            canAddOtherContainer: Boolean,
        ) {
            binding.rvRvOptions.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvRvOptions.adapter = optionFieldAdapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContainerOptionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ContainerOptionsAdapterBinding.inflate(layoutInflater, parent, false)
        return ContainerOptionsViewHolder(binding)
    }

    override fun getItemCount(): Int = listOptionsFieldAdapter.size

    override fun onBindViewHolder(
        holder: ContainerOptionsViewHolder,
        position: Int,
    ) {
        holder.bind(listOptionsFieldAdapter[position], addRecyclerView, canAddOtherContainer)
    }
}
