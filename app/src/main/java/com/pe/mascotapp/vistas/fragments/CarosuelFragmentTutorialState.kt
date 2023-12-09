package com.pe.mascotapp.vistas.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pe.mascotapp.vistas.fragments.stepTutorial.StepFive
import com.pe.mascotapp.vistas.fragments.stepTutorial.StepFour
import com.pe.mascotapp.vistas.fragments.stepTutorial.StepOne
import com.pe.mascotapp.vistas.fragments.stepTutorial.StepThree
import com.pe.mascotapp.vistas.fragments.stepTutorial.StepTwo

class CarosuelFragmentTutorialState (fragmentManager: FragmentManager, context: Context):
    FragmentStatePagerAdapter(fragmentManager)  {

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

            3 -> {
                return StepFour()
            }

            4 -> {
                return StepFive()
            }

        }
        return StepOne();
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getItemPosition(`object`: Any): Int {

        /*if (`object` is StepThree){
            val f = `object`
            f.updateImage()
        }*/

        return super.getItemPosition(`object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        //return super.getPageTitle(position)
        return when (position) {
            else -> null
        }
    }
}