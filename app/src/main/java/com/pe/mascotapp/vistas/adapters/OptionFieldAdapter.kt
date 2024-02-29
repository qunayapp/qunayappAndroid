package com.pe.mascotapp.vistas.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemOptionCalendarBinding
import com.pe.mascotapp.databinding.ItemOptionCounterBinding
import com.pe.mascotapp.databinding.ItemOptionTextBinding

class OptionFieldAdapter(val options: List<OptionViewInterface> = listOf(), val itemOnClick: () -> Unit = {}) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class OptionTextViewHolder(private val binding: ItemOptionTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: TextOption) {
            binding.tvNameOption.text = option.name
        }
    }

    class OptionCounterViewHolder(private val binding: ItemOptionCounterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: CounterOption) {
            binding.tvNameOption.text = option.name
            binding.tvCounter.text = option.counter.toString()
            binding.reduce.setOnClickListener {
                if (option.counter > 0) option.counter = option.counter - 1
                binding.tvCounter.text = option.counter.toString()
            }
            binding.reduce.setOnClickListener {
                option.counter = option.counter + 1
                binding.tvCounter.text = option.counter.toString()
            }
        }
    }

    class OptionCalendarViewHolder(private val binding: ItemOptionCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: CalendarOption) {
            binding.llOption.setOnClickListener {
                binding.line1.isVisible = !binding.line1.isVisible
                binding.calendarView.isVisible = !binding.calendarView.isVisible
            }
            binding.calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
                if (option.isNeededHour) {
                    binding.line2.isVisible = !binding.line2.isVisible
                    binding.llAddHour.isVisible = !binding.llAddHour.isVisible
                    binding.line3.isVisible = !binding.line3.isVisible
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return when (viewType) {
            R.layout.item_option_text -> OptionTextViewHolder(dataBinding as ItemOptionTextBinding)
            R.layout.item_option_calendar -> OptionCalendarViewHolder(dataBinding as ItemOptionCalendarBinding)
            else -> OptionCounterViewHolder(dataBinding as ItemOptionCounterBinding)
        }
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val option = options[position]
        when (holder) {
            is OptionTextViewHolder -> {
                holder.bind(option as TextOption)
            }

            is OptionCounterViewHolder -> {
                holder.bind(option as CounterOption)
            }

            is OptionCalendarViewHolder -> {
                holder.bind(option as CalendarOption)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (options[position].viewType) {
            OptionViewType.TextViewOption -> R.layout.item_option_text
            OptionViewType.CounterViewOption -> R.layout.item_option_counter
            OptionViewType.CalendarViewOption -> R.layout.item_option_calendar
        }
    }
}

interface OptionViewInterface {
    val viewType: OptionViewType
}

sealed class OptionViewType {
    object TextViewOption : OptionViewType()
    object CounterViewOption : OptionViewType()
    object CalendarViewOption : OptionViewType()
}

class TextOption(
    val name: String,
    override val viewType: OptionViewType.TextViewOption = OptionViewType.TextViewOption
) : OptionViewInterface


class CounterOption(
    override val viewType: OptionViewType.CounterViewOption = OptionViewType.CounterViewOption,
    val name: String,
    var counter: Int = 1
) : OptionViewInterface

class CalendarOption(
    override val viewType: OptionViewType.CalendarViewOption = OptionViewType.CalendarViewOption,
    val date: String,
    val hour: String,
    val isNeededHour: Boolean
) : OptionViewInterface

