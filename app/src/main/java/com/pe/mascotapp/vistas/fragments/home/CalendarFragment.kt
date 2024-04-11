package com.pe.mascotapp.vistas.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.FragmentCalendarBinding
import com.pe.mascotapp.utils.CalendarUtils
import com.pe.mascotapp.viewmodels.CalendarViewModel
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.CalendarReminderAdapter
import com.pe.mascotapp.vistas.adapters.DayCalendarEntity
import com.pe.mascotapp.vistas.adapters.DaysAdapter
import com.pe.mascotapp.vistas.adapters.ReminderAdapter
import com.pe.mascotapp.vistas.adapters.ReminderPetsJoinEntity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.Util
import java.time.LocalDate
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private val calendarViewModel: CalendarViewModel by viewModels()
    lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel   by viewModels()
            lateinit var selectedDate: LocalDate
    lateinit var savedDate: LocalDate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val launchCreateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        selectedDate = LocalDate.now()
        savedDate = LocalDate.now()
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.calendarView.visibility = View.GONE
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(activity, ReminderActivity::class.java)
            launchCreateReminder.launch(intent)
        }
        setViewDate(selectedDate)
        setWeekView()
        binding.icCalLeft.setOnClickListener {
            previousWeekAction()
        }
        binding.icCalRight.setOnClickListener {
            nextWeekAction()
        }
        binding.icCalShow.setOnClickListener{
            if (binding.calendarView.isVisible)   binding.calendarView.visibility = View.GONE else {
                binding.gpDayVisibility.visibility = View.GONE
                binding.calendarView.visibility = View.VISIBLE
            }
        }
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            savedDate = selectedDate
            setWeekView()
            setViewDate(savedDate)
            viewModel.getReminders(0, savedDate )
            binding.gpDayVisibility.visibility = View.VISIBLE
            binding.calendarView.visibility = View.GONE
        }
        viewModel.getReminders(0, savedDate)
        viewModel.listReminders.observe(viewLifecycleOwner) {
            binding.rvReminders.apply {
                this.adapter = CalendarReminderAdapter(it)
                this.layoutManager = LinearLayoutManager(context)
            }
        }
        return binding.root;
    }

    fun previousWeekAction() {
        selectedDate = selectedDate.minusWeeks(1)

        setWeekView()
    }
    fun nextWeekAction() {
        selectedDate = selectedDate.plusWeeks(1)
        setWeekView()
    }
    fun setViewDate(day: LocalDate){
        binding.tvDaySelect.text = day.dayOfMonth.toString()
        binding.tvMonthYears.text = CalendarUtils.formatMonthYear(day,Locale("es", "ES"))
        binding.tvNameDay.text = CalendarUtils.getAbbreviatedDayName(day,Locale("es", "ES")).capitalize()
        binding.calendarView.setDate(CalendarUtils.convertLocalDateToCalendar(savedDate).timeInMillis, false, true)
    }
    private fun setWeekView() {
        val days: ArrayList<LocalDate> = CalendarUtils.daysInWeekArray(selectedDate)
        val dayEntities =  ArrayList(days.map { DayCalendarEntity(it,it==savedDate) })
        val calendarAdapter = DaysAdapter(dayEntities){day, position ->
            savedDate=day
            selectedDate = day
            viewModel.getReminders(0, savedDate )
            setViewDate(day)
            setWeekView()
        }
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 7)
        binding.calendarRecyclerView.setLayoutManager(layoutManager)
        binding.calendarRecyclerView.setAdapter(calendarAdapter)
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