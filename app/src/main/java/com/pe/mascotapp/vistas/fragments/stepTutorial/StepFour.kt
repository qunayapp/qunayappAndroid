package com.pe.mascotapp.vistas.fragments.stepTutorial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.fragments.stepRegister.StepOne

class StepFour : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_tutorial_three, container,false)
        return view
    }

    companion object {
        fun newInstance() : StepFour {
            val stepFour = StepFour()
            return stepFour
        }
    }
}