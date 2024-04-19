package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemDayBinding
import com.pe.mascotapp.utils.CalendarUtils
import java.time.LocalDate
import java.util.Locale

class DaysAdapter(
    val days: ArrayList<DayCalendarEntity>,
    val itemOnClick: (day: LocalDate, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION
    private var dateSelected: LocalDate = LocalDate.now()

    class DaysViewHolder(val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: DayCalendarEntity) {
            binding.cellDayText.text = if (date == null) "" else
                date.day.dayOfMonth.toString()
            binding.cellDayLetter.text =
                if (date == null) "" else CalendarUtils.getAbbreviatedDayName(
                    date.day,
                    Locale("es", "ES")
                ).first().toString().toUpperCase()
            val  finalCounter = minOf(date.counter, 3)
            repeat(finalCounter) {
                val imageView = ImageView(binding.root.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                       15,
                        15// Set your image width
                        // Set your image height
                    )
                    setImageResource(R.drawable.ic_rectangle_border_28)
                    setImageTintList(ContextCompat.getColorStateList(context, if (date.isSelected) R.color.white else  R.color.black ))
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setPadding(
                        2,
                        2,
                        2,
                        2
                    )
                }
                binding.llCounter.addView(imageView)
            }
            if (date.isSelected) {
                binding.parentView.setBackgroundResource(R.drawable.ic_rectangle_border_28)
                binding.cellDayText.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.cellDayLetter.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                binding.parentView.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.parentView.context,
                        R.color.white
                    )
                )
                binding.cellDayLetter.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.third
                    )
                )
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
        val currentDate = days[position]
        holder.bind(days[position])
        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                dateSelected = currentDate.day
                selectedPosition = position
                itemOnClick(currentDate.day, position)
            }
        }
    }
}

class DayCalendarEntity(
    val day: LocalDate,
    val isSelected: Boolean,
    val counter: Int

)