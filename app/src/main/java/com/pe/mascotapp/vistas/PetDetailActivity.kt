package com.pe.mascotapp.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityPetDetailBinding

class PetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}