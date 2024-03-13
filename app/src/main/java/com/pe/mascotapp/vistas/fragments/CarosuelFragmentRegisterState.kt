package com.pe.mascotapp.vistas.fragments

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.fragments.stepRegister.StepOne
import com.pe.mascotapp.vistas.fragments.stepRegister.StepThree
import com.pe.mascotapp.vistas.fragments.stepRegister.StepTwo

class CarosuelFragmentRegisterState( val fragmentList: List<Fragment>, fragmentManager: FragmentActivity):FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position];
    }

}