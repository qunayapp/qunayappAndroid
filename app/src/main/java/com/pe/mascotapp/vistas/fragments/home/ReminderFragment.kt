package com.pe.mascotapp.vistas.fragments.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.FragmentReminderfragmentBinding
import com.pe.mascotapp.viewmodels.ReminderHistoryViewModel
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.ReminderAdapter
import com.pe.mascotapp.vistas.adapters.TabAnimalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment : Fragment() {

    private val viewModel: ReminderHistoryViewModel by viewModels()

    private val launchCreateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) viewModel.getReminders()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReminderfragmentBinding.inflate(inflater, container, false)
        binding.reminderViewModel = viewModel
        binding.rvAnimalsReminder.apply {
            this.adapter = TabAnimalAdapter(listOf())
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvReminders.apply {
            this.adapter = ReminderAdapter(listOf())
            this.layoutManager = LinearLayoutManager(context)
        }
        binding.btnAddNew.setOnClickListener {
            val intent = Intent(activity, ReminderActivity::class.java)
            launchCreateReminder.launch(intent)
        }
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(activity, ReminderActivity::class.java)
            launchCreateReminder.launch(intent)
        }
        viewModel.listPets.observe(viewLifecycleOwner) {
            (binding.rvAnimalsReminder.adapter as TabAnimalAdapter).tabAnimals = it
            (binding.rvAnimalsReminder.adapter as TabAnimalAdapter).notifyDataSetChanged()
        }
        viewModel.listReminders.observe(viewLifecycleOwner) {
            (binding.rvReminders.adapter as ReminderAdapter).reminders = it
            (binding.rvReminders.adapter as ReminderAdapter).notifyDataSetChanged()
        }
        viewModel.getAnimalTabs()
        viewModel.getReminders()
        return binding.root
    }

    companion object {
        fun newInstance(): Fragment {
            return ReminderFragment()
        }
    }
}