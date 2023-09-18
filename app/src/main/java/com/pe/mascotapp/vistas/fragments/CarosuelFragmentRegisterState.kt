package com.pe.mascotapp.vistas.fragments

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.fragments.stepRegister.StepOne
import com.pe.mascotapp.vistas.fragments.stepRegister.StepThree
import com.pe.mascotapp.vistas.fragments.stepRegister.StepTwo

class CarosuelFragmentRegisterState(fragmentManager: FragmentManager, context: Context):FragmentStatePagerAdapter(fragmentManager) {

    var contexto = context

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return StepOne()
            }
            1 -> {
                return StepTwo()
            }
            2 -> {
                return StepThree()
            }
        }
        return StepOne();
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItemPosition(`object`: Any): Int {

        if (`object` is StepThree){
            val f = `object`
            f.updateImage()
        }

        return super.getItemPosition(`object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //return super.getPageTitle(position)
        return when (position) {
            else -> null
        }
    }

}