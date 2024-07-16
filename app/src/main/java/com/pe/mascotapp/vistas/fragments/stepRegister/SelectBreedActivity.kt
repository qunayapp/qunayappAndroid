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
            intent.getParcelableArrayListExtra<BreedPetEntity>(BUNDLE_BREED) ?: arrayListOf()
        val kindPet = intent.getStringExtra(BUNDLE_KIND_PET)
        binding.abComposeView.setContent {
            SelectBreedPetsScreen(listBreed, getKindPet(kindPet))
        }
        setContentView(binding.root)
    }

    companion object {
        const val BUNDLE_BREED = "BUNDLE_BREED"
        const val BUNDLE_KIND_PET = "BUNDLE_KIND_PET"
        fun newInstance(activity: Activity, breeds: List<BreedPetEntity>, kindPet: String): Intent {
            val intent = Intent(activity, SelectBreedActivity::class.java)
            intent.putParcelableArrayListExtra(BUNDLE_BREED, ArrayList(breeds))
            intent.putExtra(BUNDLE_KIND_PET, kindPet)
            return intent
        }
    }
}