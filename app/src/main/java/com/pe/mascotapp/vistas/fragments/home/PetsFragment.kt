package com.pe.mascotapp.vistas.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.FragmentPetsBinding
import com.pe.mascotapp.viewmodels.PetsMainViewModel
import com.pe.mascotapp.vistas.adapters.PetMainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsFragment : Fragment() {
    private val petsMainViewModel:PetsMainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentPetsBinding.inflate(inflater, container, false)
        setUpObservables(binding)
        return binding.root;
    }
    private fun setUpObservables(binding: FragmentPetsBinding) {
        binding.rvPets.apply {
            this.adapter = PetMainAdapter(petsMainViewModel.getPets()){
                Log.d("yoelkill","onclkcick")
            }
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
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