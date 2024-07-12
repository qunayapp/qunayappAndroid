package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pe.mascotapp.databinding.ActivityBreedBinding
import java.util.ArrayList

class SelectBreedActivity : AppCompatActivity() {

    lateinit var binding: ActivityBreedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedBinding.inflate(layoutInflater)
        val listBreed =
            intent.getParcelableArrayListExtra<BreedPetEntity>("BUNDLE_BREED") ?: arrayListOf()
        binding.abComposeView.setContent {
            SelectBreedPetsScreen(listBreed)
        }
        setContentView(binding.root)
    }

    companion object {
        fun newInstance(activity: Activity, breeds: List<BreedPetEntity>): Intent {
            val intent = Intent(activity, SelectBreedActivity::class.java)
            intent.putParcelableArrayListExtra("BUNDLE_BREED", ArrayList(breeds))
            return intent
        }
    }
}