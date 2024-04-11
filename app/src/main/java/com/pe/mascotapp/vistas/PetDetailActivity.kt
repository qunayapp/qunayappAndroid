package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityPetDetailBinding
import com.pe.mascotapp.vistas.entities.PetEntity


class PetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetDetailBinding
    private lateinit var petEntity: PetEntity
    var isEditableGeneralInfo :Boolean=true
    var isEditableCareInfo  :Boolean=true
    var isEditableHealthInfo  :Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetDetailBinding.inflate(layoutInflater)
        binding.toolbar.subTitle.text = getString(R.string.my_pets)

/*        binding.toolbar.btnBack.setOnClickListener {
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle back button press event here
                    // For example, navigate back or perform any necessary actions
                }
            }

            onBackPressedDispatcher.addCallback(this@PetDetailActivity, callback)
        }*/
        petEntity = intent.getParcelableExtra<PetEntity>("petEntity")!!
        setUpValues()
        setListener()
        setContentView(binding.root)
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun setListener(){
        binding.txtGeneralInfo.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2 // Index of the right drawable
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (event.rawX  >= ( binding.txtGeneralInfo.right -  binding.txtGeneralInfo.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    isEditableGeneralInfo= setEditableView(binding.llGeneralInfo,!isEditableGeneralInfo)
                    return@setOnTouchListener true
                }
            }
           false
        }
        binding.txtCareInfo.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2 // Index of the right drawable
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (event.rawX  >= ( binding.txtCareInfo.right -  binding.txtCareInfo.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    isEditableCareInfo= setEditableView(binding.llCareInfo,!isEditableCareInfo)
                    return@setOnTouchListener true
                }
            }
            false
        }
        binding.txtHealthInfo.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2 // Index of the right drawable
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (event.rawX  >= ( binding.txtHealthInfo.right -  binding.txtHealthInfo.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    isEditableHealthInfo= setEditableView(binding.llHealthInfo,!isEditableHealthInfo)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun  setUpValues(){
        binding.petEntity = petEntity
        Glide.with(binding.root.context)
            .load(petEntity.image)
            .placeholder(R.drawable.perro1)
            .error(R.drawable.perro1)
            .into(binding.petImage)
    }

    private fun setEditableView(linearLayout:LinearLayoutCompat,editable:Boolean):Boolean{
        for (i in 0 until linearLayout.childCount) {
            val child = linearLayout.getChildAt(i)
            if (child is EditText) {
                child.isEnabled = editable // Set it to true or false based on your requirement
                // or
                // editText.setFocusable(false); // Set it to true or false based on your requirement
            }
        }
        return  editable
    }
}