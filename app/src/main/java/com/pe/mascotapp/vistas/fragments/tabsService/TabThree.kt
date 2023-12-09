package com.pe.mascotapp.vistas.fragments.tabsService

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pe.mascotapp.R

class TabThree : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_step_three, container,false)
        return view
    }

    companion object {
        fun newInstance() : TabOne {
            val tabOne = TabOne()
            return tabOne
        }
    }
}