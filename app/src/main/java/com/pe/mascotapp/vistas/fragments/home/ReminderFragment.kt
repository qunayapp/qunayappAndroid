package com.pe.mascotapp.vistas.fragments.home

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

    private val reminderViewModel: ReminderHistoryViewModel by viewModels()

    private val launchCreateReminder =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReminderfragmentBinding.inflate(inflater, container, false)
        binding.rvAnimalsReminder.apply {
            this.adapter = TabAnimalAdapter(reminderViewModel.getAnimalTabs())
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvReminders.apply {
            this.adapter = ReminderAdapter(reminderViewModel.getReminders())
            this.layoutManager = LinearLayoutManager(context)
        }
        binding.btnAddNew.setOnClickListener {
            val intent = Intent(activity, ReminderActivity::class.java)
            launchCreateReminder.launch(intent)
        }
        return binding.root;
    }

    companion object {
        fun newInstance(): Fragment {
            return ReminderFragment()
        }
    }
}