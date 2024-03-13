package com.pe.mascotapp.vistas.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemDayBinding
import java.time.LocalDate

class DaysAdapter(
    val days: ArrayList<LocalDate>,
    val itemOnClick: (day: LocalDate, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var dateSelected :LocalDate = LocalDate.now()
    class DaysViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: LocalDate?, isSelected: Boolean) {
            if (date == null) binding.cellDayText.text else
                binding.cellDayText.text = if (date == null) "" else
                    date.dayOfMonth.toString()
            if (isSelected) {
                binding.parentView.setBackgroundResource(R.drawable.ic_rectangle_border_3)
                binding.cellDayText.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            }else{
                binding.parentView.setBackgroundColor(ContextCompat.getColor(binding.parentView.context, R.color.white))
                binding.cellDayText.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(inflater, parent, false)
        val layoutParams = binding.root.layoutParams
        if (days.size > 15) {
            layoutParams.height = (parent.height * 0.166666666).toInt()
        } else {
            layoutParams.height = parent.height
        }
        return DaysViewHolder(binding)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind(days[position], days[position] == dateSelected)
        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            dateSelected = days[position]
            notifyItemChanged(position)
            itemOnClick(days[position], position)
        }
    }
}