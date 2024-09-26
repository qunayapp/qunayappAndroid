package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.OnEditTextChanged
import com.pe.mascotapp.utils.Utils
import java.util.*

import android.widget.RadioButton

import android.widget.RadioGroup
import androidx.compose.ui.platform.ComposeView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pe.mascotapp.databinding.FragmentRegisterOneBinding
import com.pe.mascotapp.modelos.Usuario
import com.pe.mascotapp.vistas.CarosuelRegisterActivity


class StepOne():Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentRegisterOneBinding.inflate(inflater, container, false)
        binding.composeViewStepOne.setContent {
            StepOneScreen(
                usuario = (activity as? CarosuelRegisterActivity)?.usuario ?: Usuario() 
            )
        }
        return binding.root;
    }


    companion object {
        fun newInstance(text: String) : StepOne {
            val stepOne = StepOne()
            val args = Bundle()
            args.putString("title", text)
            stepOne.arguments = args
            return stepOne
        }
    }
}