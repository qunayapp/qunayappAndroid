package com.pe.mascotapp.vistas.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemOptionCalendarBinding
import com.pe.mascotapp.databinding.ItemOptionCounterBinding
import com.pe.mascotapp.databinding.ItemOptionSheduleBinding
import com.pe.mascotapp.databinding.ItemOptionTextBinding
import com.pe.mascotapp.extentions.getTime
import com.pe.mascotapp.utils.CalendarUtils
import java.util.Date

class OptionFieldAdapter(private val options: List<OptionViewInterface> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var positionOptionTextSelected = -1

    class OptionTextViewHolder(private val binding: ItemOptionTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: TextOption, optionClick: (position: Int) -> Unit) {
            binding.llOption.setOnClickListener {
                optionClick(adapterPosition)
            }
            if (option.isSelected) {
                binding.tvNameOption.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            } else {
                binding.tvNameOption.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            }
            binding.tvNameOption.text = option.name
            val bg = if (adapterPosition % 2 == 0) R.color.white else R.color.gray100
            binding.llOption.setBackgroundColor(ContextCompat.getColor(binding.root.context, bg))
        }
    }

    class OptionCounterViewHolder(private val binding: ItemOptionCounterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: CounterOption, optionClick: (position: Int) -> Unit) {
            binding.tvNameOption.text = option.name
            binding.tvCounter.text = option.counter.toString()
            binding.tvCounter.text = if (option.isSelected) option.counter.toString() else "0"
            binding.reduce.setOnClickListener {
                optionClick(adapterPosition)
                if (option.counter > 0) option.counter -= 1
                binding.tvCounter.text = option.counter.toString()
            }
            binding.add.setOnClickListener {
                optionClick(adapterPosition)
                option.counter += 1
                binding.tvCounter.text = option.counter.toString()
            }
            val bg = if (adapterPosition % 2 == 0) R.color.white else R.color.gray100
            binding.llOption.setBackgroundColor(ContextCompat.getColor(binding.root.context, bg))
        }
    }

    class OptionCalendarViewHolder(private val binding: ItemOptionCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCalendarHour(option: CalendarHourOption, optionClick: (position: Int) -> Unit) {

            binding.tvNameOption.text = option.name
            binding.gpHourCalendar.isVisible = false

            val bg = if (adapterPosition % 2 == 0) R.color.white else R.color.gray100
            binding.llOption.setBackgroundColor(ContextCompat.getColor(binding.root.context, bg))

            val lisOfViewNoCalendarHour = arrayListOf(binding.line1, binding.line2, binding.line3)
            if (!option.isSelected) lisOfViewNoCalendarHour.addAll(listOf(binding.llAddSchedule, binding.tpHourSelect, binding.calendarView))

            lisOfViewNoCalendarHour.forEach { it.isVisible = false }

            binding.llOption.setOnClickListener {
                listOf(binding.llAddSchedule, binding.tpHourSelect, binding.calendarView).forEach { it.isVisible = !it.isVisible }
                binding.ivArrow.rotation = if (binding.gpHourCalendar.isVisible) 0f else 90f
                option.date = Date(binding.calendarView.date)
                option.hour = binding.tpHourSelect.getTime()
                if (!it.isSelected) optionClick(adapterPosition)
            }
            binding.llAddSchedule.setOnClickListener {
                binding.line3.isVisible = !binding.line3.isVisible
                binding.tpHourSelect.isVisible = !binding.tpHourSelect.isVisible
                binding.ivArrowHour.rotation = if (!binding.line3.isVisible) 90f else 0f
            }
            binding.calendarView.setOnDateChangeListener { _, year, month, day ->
                val selectedDate = "$day / ${month + 1} / $year"
                option.date = CalendarUtils.getTime(year, month, day)
                binding.tvNameOption.text = selectedDate
            }
            binding.tpHourSelect.setOnTimeChangedListener { _, _, _ ->
                option.hour = binding.tpHourSelect.getTime()
            }
        }

        fun bindSimpleCalendar(option: CalendarSimple) {
            val bg = if (adapterPosition % 2 == 0) R.color.white else R.color.gray100
            binding.llOption.setBackgroundColor(ContextCompat.getColor(binding.root.context, bg))
            val lisOfViewNoCalendarHour = listOf(
                binding.line1,
                binding.line2,
                binding.line3,
                binding.llAddSchedule,
                binding.tpHourSelect,
                binding.llOption
            )
            lisOfViewNoCalendarHour.forEach { it.isVisible = !it.isVisible }
            binding.calendarView.setOnDateChangeListener { _, year, month, day ->
                option.date = CalendarUtils.getTime(year, month, day)
            }
        }

        fun bindNormalCalendar(option: CalendarOptionNormal, optionClick: (position: Int) -> Unit) {

            val bg = if (adapterPosition % 2 == 0) R.color.white else R.color.gray100
            binding.llOption.setBackgroundColor(ContextCompat.getColor(binding.root.context, bg))

            val listOfViewsNoNormal = listOf(binding.line1, binding.line2, binding.line3, binding.calendarView, binding.llAddSchedule, binding.tpHourSelect)
            listOfViewsNoNormal.forEach { it.isVisible = false }

            binding.tvNameOption.text = if (option.isSelected) option.date?.let { CalendarUtils.getFormatDate3(it) } else option.name

            binding.llOption.setOnClickListener {
                binding.line1.isVisible = !binding.line1.isVisible
                binding.calendarView.isVisible = !binding.calendarView.isVisible
                if (binding.calendarView.isVisible) {
                    binding.ivArrow.rotation = 0f
                } else {
                    binding.ivArrow.rotation = 90f
                }
            }

            binding.calendarView.setOnDateChangeListener { _, year, month, day ->
                val selectedDate = "$day / ${month + 1} / $year"
                option.date = CalendarUtils.getTime(year, month, day)
                binding.tvNameOption.text = selectedDate
                optionClick(adapterPosition)
            }
        }

    }


    class ScheduleViewHolder(private val binding: ItemOptionSheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: ScheduleOption) {
            binding.tvTitleSchedule.text = option.name
            option.hour = binding.tpSelect.getTime()
            binding.tpSelect.setOnTimeChangedListener { _, _, _ ->
                option.hour = binding.tpSelect.getTime()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return when (viewType) {
            R.layout.item_option_text -> OptionTextViewHolder(dataBinding as ItemOptionTextBinding)
            R.layout.item_option_calendar -> OptionCalendarViewHolder(dataBinding as ItemOptionCalendarBinding)
            R.layout.item_option_counter -> OptionCounterViewHolder(dataBinding as ItemOptionCounterBinding)
            else -> ScheduleViewHolder(dataBinding as ItemOptionSheduleBinding)
        }
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val option = options[position]
        when (holder) {
            is OptionTextViewHolder -> {
                holder.bind(option as TextOption) { updateOption(position) }
            }

            is OptionCounterViewHolder -> {
                holder.bind(option as CounterOption) { updateOption(position) }
            }

            is OptionCalendarViewHolder -> {
                when (option) {
                    is CalendarHourOption -> holder.bindCalendarHour(option) { updateOption(position) }
                    is CalendarSimple -> holder.bindSimpleCalendar(option)

                    is CalendarOptionNormal -> holder.bindNormalCalendar(option) { updateOption(position) }
                }
            }

            is ScheduleViewHolder -> {
                holder.bind(option as ScheduleOption)
            }
        }
    }

    private fun updateOption(positionSelected: Int) {
        if (positionSelected != positionOptionTextSelected) {
            val tempPosition = positionOptionTextSelected
            positionOptionTextSelected = positionSelected
            options[positionOptionTextSelected].isSelected = true
            notifyItemChanged(positionOptionTextSelected)
            if (tempPosition != -1) {
                options[tempPosition].isSelected = false
                notifyItemChanged(tempPosition)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (options[position].viewType) {
            OptionViewType.TextViewOption -> R.layout.item_option_text
            OptionViewType.CounterViewOption -> R.layout.item_option_counter
            OptionViewType.CalendarViewOption -> R.layout.item_option_calendar
            OptionViewType.ScheduleViewOption -> R.layout.item_option_shedule
        }
    }
}

interface OptionViewInterface {
    val viewType: OptionViewType
    var isSelected: Boolean
}

sealed class OptionViewType {
    object TextViewOption : OptionViewType()
    object CounterViewOption : OptionViewType()
    object CalendarViewOption : OptionViewType()
    object ScheduleViewOption : OptionViewType()
}

enum class ValueTextOption {
    DONT_REPEAT,
    ALL_DAYS,
    MONDAY_FRIDAY,
    ALL_WEEKS,
    ALL_MONTHS,
    ALL_YEARS,
    MINUTES_15,
    MINUTES_30,
    MINUTES_HOUR,
    FOR_EVER
}

class TextOption(
    val name: String,
    val value: ValueTextOption,
    override var isSelected: Boolean = false,
    override val viewType: OptionViewType.TextViewOption = OptionViewType.TextViewOption
) : OptionViewInterface


class CounterOption(
    val name: String,
    var counter: Int = 1,
    override var isSelected: Boolean = false,
    override val viewType: OptionViewType.CounterViewOption = OptionViewType.CounterViewOption
) : OptionViewInterface

open class CalendarOption(
    val name: String,
    var date: Date? = null,
    var hour: String? = null,
    override var isSelected: Boolean = false,
    override val viewType: OptionViewType.CalendarViewOption = OptionViewType.CalendarViewOption,
) : OptionViewInterface

class CalendarOptionNormal(name: String) : CalendarOption(name)

class CalendarHourOption(name: String) : CalendarOption(name)

class CalendarSimple(name: String) : CalendarOption(name)

class ScheduleOption(
    val name: String,
    var hour: String? = null,
    override var isSelected: Boolean = false,
    override val viewType: OptionViewType.ScheduleViewOption = OptionViewType.ScheduleViewOption,
) : OptionViewInterface
