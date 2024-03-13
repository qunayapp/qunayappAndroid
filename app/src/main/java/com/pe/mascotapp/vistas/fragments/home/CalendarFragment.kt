package com.pe.mascotapp.vistas.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
import com.pe.mascotapp.vistas.adapters.DaysAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private val calendarViewModel: CalendarViewModel by viewModels()
    lateinit var binding: FragmentCalendarBinding
    lateinit var selectedDate: LocalDate
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
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.rvReminders.apply {
            this.adapter = CalendarReminderAdapter(calendarViewModel.getReminders())
            this.layoutManager = LinearLayoutManager(context)
        }
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
    }
    private fun setWeekView() {
        val days: ArrayList<LocalDate> = CalendarUtils.daysInWeekArray(selectedDate)

        val calendarAdapter = DaysAdapter(days){day, position ->
            selectedDate = day
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