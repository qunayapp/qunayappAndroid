package com.pe.mascotapp.vistas.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.FragmentCalendarBinding
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.utils.dateToLocalDate
import com.pe.mascotapp.viewmodels.CalendarViewModel
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.CalendarReminderAdapter
import com.pe.mascotapp.vistas.adapters.DayCalendarEntity
import com.pe.mascotapp.vistas.adapters.DaysAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by viewModels()
    private val launchCreateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.getAllReminders(LocalDate.now())
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        setUpRecyclerViews()
        binding.calendarView.visibility = View.GONE
        binding.llCalendar.visibility = View.GONE
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(activity, ReminderActivity::class.java)
            launchCreateReminder.launch(intent)
        }
        binding.icCalLeft.setOnClickListener {
            previousWeekAction()
        }
        binding.icCalRight.setOnClickListener {
            nextWeekAction()
        }
        binding.btnNext.setOnClickListener {
            binding.calendarView.scrollRight()
        }
        binding.btnPrevious.setOnClickListener {
            binding.calendarView.scrollLeft()
        }
        binding.calendarView.setLocale(TimeZone.getTimeZone("America/Lima"), Locale("es", "PE"))
        binding.calendarView.setDayColumnNames(arrayOf("L","M","M","J","V","S","D"))
        binding.icCalShow.setOnClickListener {
            if (binding.calendarView.isVisible) {
                binding.calendarView.visibility = View.GONE
                binding.llCalendar.visibility = View.GONE
            } else {
                binding.gpDayVisibility.visibility = View.GONE
                binding.calendarView.visibility = View.VISIBLE
                binding.llCalendar.visibility = View.VISIBLE
            }
        }

        binding.calendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                viewModel.updateSelectDate(dateToLocalDate(dateClicked))
                binding.gpDayVisibility.visibility = View.VISIBLE
                binding.llCalendar.visibility = View.GONE
                binding.calendarView.visibility = View.GONE
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                binding.titleDate.text =
                    firstDayOfNewMonth?.let { CalendarUtils.formatMonthYear(it).capitalize() }
                firstDayOfNewMonth?.let { viewModel.updateSelectDate(dateToLocalDate(it)) }
            }
        })
        viewModel.listRemindersByDateCount.observe(viewLifecycleOwner) {
            updateListReminder()
            val days = viewModel.reminderByDate.keys.map {
                Event(
                    R.color.blue_primary,
                    it.atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                )
            }
            (binding.calendarRecyclerView.adapter as? DaysAdapter)?.days?.firstOrNull()?.let {
                setWeekView(it.day)
            }
            binding.calendarView.removeAllEvents()
            binding.calendarView.addEvents(days)
        }
        viewModel.selectedDate.observe(viewLifecycleOwner) {
            setViewDate()
            setWeekView(it)
            updateListReminder()
            viewModel.getAllReminders(it.withDayOfMonth(1))
        }
        return binding.root;
    }

    private fun updateListReminder() {
        val day = viewModel.selectedDate.value ?: LocalDate.now()
        (binding.rvReminders.adapter as? CalendarReminderAdapter)?.apply {
            reminders = viewModel.reminderByDate[day] ?: listOf()
            notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerViews() {
        binding.rvReminders.apply {
            this.adapter = CalendarReminderAdapter(listOf())
            this.layoutManager = LinearLayoutManager(context)
        }
        binding.calendarRecyclerView.apply {
            this.adapter = DaysAdapter(arrayListOf()) { day, _ ->
                viewModel.updateSelectDate(day)
            }
            this.layoutManager = GridLayoutManager(context, 7)
        }
    }

    private fun previousWeekAction() {
        (binding.calendarRecyclerView.adapter as? DaysAdapter)?.days?.firstOrNull()?.let {
            val previousWeek = it.day.minusWeeks(1)
            val firstDateInWeek = CalendarUtils.daysInWeekArray(previousWeek).first()
            setWeekView(previousWeek)
            if (firstDateInWeek.dayOfMonth in 28..31) {
                viewModel.getAllReminders(firstDateInWeek.withDayOfMonth(1))
                return
            }
        }
    }

    private fun nextWeekAction() {
        (binding.calendarRecyclerView.adapter as? DaysAdapter)?.days?.firstOrNull()?.let {
            val nextWeek = it.day.plusWeeks(1)
            val lastDateWeek = CalendarUtils.daysInWeekArray(nextWeek).last()
            setWeekView(nextWeek)
            if (lastDateWeek.dayOfMonth in 1..7) {
                viewModel.getAllReminders(lastDateWeek.withDayOfMonth(1))
                return
            }
        }
    }

    private fun setViewDate() {
        val day = viewModel.selectedDate.value ?: LocalDate.now()
        binding.tvDaySelect.text = day.dayOfMonth.toString()
        binding.tvMonthYears.text = CalendarUtils.formatMonthYear(day, Locale("es", "ES"))
        binding.tvNameDay.text =
            CalendarUtils.getAbbreviatedDayName(day, Locale("es", "ES")).capitalize()
        binding.calendarView.setCurrentDate(CalendarUtils.convertLocalDateToDate(day))
        binding.titleDate.text = CalendarUtils.formatMonthYear(day, Locale("es", "ES")).capitalize()
    }

    private fun setWeekView(selectedDate: LocalDate) {
        val days: ArrayList<LocalDate> = CalendarUtils.daysInWeekArray(selectedDate)
        val dayEntities = ArrayList(days.map { DayCalendarEntity(it, it == viewModel.selectedDate.value, viewModel.reminderByDateCount[it]?:0) })
        (binding.calendarRecyclerView.adapter as? DaysAdapter)?.apply {
            this.days = dayEntities
            this.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CalendarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}