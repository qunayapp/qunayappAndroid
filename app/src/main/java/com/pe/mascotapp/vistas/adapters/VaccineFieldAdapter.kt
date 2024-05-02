package com.pe.mascotapp.vistas.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ItemFieldVaccineBinding
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity

class VaccineFieldAdapter(
    private val vaccineFields: List<VaccineFieldEntity>,
    var addVaccineField: () -> Unit = {},
    var removeVaccine: (position: Int) -> Unit = {},
    var validateSelected: () -> Unit = {},
) :
    RecyclerView.Adapter<VaccineFieldAdapter.VaccineFieldAdapterViewHolder>() {
    class VaccineFieldAdapterViewHolder(private val binding: ItemFieldVaccineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            vaccineField: VaccineFieldEntity,
            isFirstItem: Boolean,
            actionVaccineField: (position: Int?) -> Unit,
            validateSelected: () -> Unit = {},
        ) {
            binding.tvTitleField.isVisible = isFirstItem
            if (vaccineField.nameSelected.isNotEmpty()) binding.autoService.setText(vaccineField.nameSelected)
            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    itemView.context,
                    R.layout.simple_dropdown_item_1line,
                    listOf(
                        "Cuadruple",
                        "Quintuple",
                        "Sextuple",
                        "Distemper canino o moquillo",
                        "Parvovirus",
                        "Parainfluenza",
                        "Coronavirus",
                        "Antirrabica",
                        "Leptospirosis",
                        "Tos de las perras(KC)",
                    ),
                )
            binding.autoService.setAdapter(adapter)
            binding.autoService.setOnItemClickListener { parent, _, position, _ ->
                vaccineField.nameSelected = parent.getItemAtPosition(position).toString()
                validateSelected()
            }
            binding.tvActionField.text = if (isFirstItem) "+agregar" else "-remover"
            binding.tvActionField.setOnClickListener {
                val tempPosition = if (isFirstItem) null else adapterPosition
                actionVaccineField(tempPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VaccineFieldAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFieldVaccineBinding.inflate(layoutInflater, parent, false)
        return VaccineFieldAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = vaccineFields.size

    override fun onBindViewHolder(
        holder: VaccineFieldAdapterViewHolder,
        position: Int,
    ) {
        holder.bind(vaccineFields[position], position == 0, actionVaccineField = {
            actionVaccineField(it)
        }, validateSelected = { validateSelected() })
    }

    private fun actionVaccineField(position: Int?) {
        position?.let {
            if (position != -1) removeVaccine(it)
        } ?: apply {
            addVaccineField()
        }
    }
}
