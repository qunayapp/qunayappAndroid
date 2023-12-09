package com.pe.mascotapp.vistas.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pe.mascotapp.vistas.fragments.tabsService.TabOne
import com.pe.mascotapp.vistas.fragments.tabsService.TabTwo

class DetailServiceFragmentState (fragmentManager: FragmentManager, context: Context):
    FragmentStatePagerAdapter(fragmentManager)  {

    var contexto = context

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TabOne()
            }
            1 -> {
                return TabTwo()
            }

        }
        return TabOne();
    }

    override fun getCount(): Int {
        return 2
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
            0 -> {
                return "Info"
            }
            1 -> {
                return "Reviews"
            }
            else -> null


        }
    }
}