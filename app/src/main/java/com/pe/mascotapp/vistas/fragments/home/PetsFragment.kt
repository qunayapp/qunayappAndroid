package com.pe.mascotapp.vistas.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.FragmentPetsBinding
import com.pe.mascotapp.viewmodels.PetsMainViewModel
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import com.pe.mascotapp.vistas.LoadingActivity
import com.pe.mascotapp.vistas.PetDetailActivity
import com.pe.mascotapp.vistas.ReminderActivity
import com.pe.mascotapp.vistas.adapters.PetMainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsFragment : Fragment() {
    private val petsMainViewModel:PetsMainViewModel by viewModels()
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
        val binding = FragmentPetsBinding.inflate(inflater, container, false)
        setUpObservables(binding)
        setListeners(binding)
        return binding.root;
    }
    private fun setUpObservables(binding: FragmentPetsBinding) {
        binding.rvPets.apply {
            this.adapter = PetMainAdapter(petsMainViewModel.getPets()){
                val intent = Intent(activity, PetDetailActivity::class.java)
                intent.putExtra("petEntity", it)
                startActivity(intent)
            }
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun setListeners(binding: FragmentPetsBinding){
        binding.btnAgregar.setOnClickListener {
            val intent = Intent(activity, CarosuelRegisterActivity::class.java)
            val bundle = Bundle()
            bundle.putBoolean("isFromMainPets", true)
            intent.putExtras(bundle)
            launchCreateReminder.launch(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PetsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}