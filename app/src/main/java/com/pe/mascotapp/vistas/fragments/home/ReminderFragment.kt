package com.pe.mascotapp.vistas.fragments.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.NestedScrollView
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
    var pageNumber: Int = 0
    lateinit var binding : FragmentReminderfragmentBinding

    private val launchCreateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                pageNumber = 0
                (binding.rvReminders.adapter as ReminderAdapter).clearList()
                viewModel.getReminders(pageNumber)
            }
        }

    private val launchUpdateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                pageNumber = 0
                (binding.rvReminders.adapter as ReminderAdapter).clearList()
                viewModel.getReminders(pageNumber)
            }
        }

    override fun onPause() {
        binding.nsvReminders.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, _, _, _ -> },
        )
        viewModel.cancelReminder()
        super.onPause()
    }

    override fun onResume() {
        binding.nsvReminders.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    if ((binding.rvReminders.adapter as ReminderAdapter).reminders.isNotEmpty()) pageNumber++
                    viewModel.getReminders(pageNumber)
                }
            },
        )
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReminderfragmentBinding.inflate(inflater, container, false)
        binding.reminderViewModel = viewModel
        binding.rvAnimalsReminder.apply {
            this.adapter =
                TabAnimalAdapter(listOf()) {
                    viewModel.filterPets(it)
                }
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvReminders.apply {
            this.adapter =
                ReminderAdapter(mutableListOf(), {
                    viewModel.updateReminder(it)
                }, {
                    val intent = Intent(activity, ReminderActivity::class.java)
                    intent.putExtra("BUNDLE_REMINDER", it)
                    launchUpdateReminder.launch(intent)
                })
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
            (binding.rvReminders.adapter as ReminderAdapter).addItems(it)
        }
        viewModel.getAnimalTabs()
        pageNumber = 0
        viewModel.getReminders(pageNumber)
        return binding.root
    }

    companion object {
        fun newInstance(): Fragment {
            return ReminderFragment()
        }
    }
}
