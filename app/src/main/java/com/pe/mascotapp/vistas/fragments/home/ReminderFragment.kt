package com.pe.mascotapp.vistas.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pe.mascotapp.R

class ReminderFragment : Fragment() {

    var btnAgregar:MaterialButton ?= null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_reminderfragment, container,false)
        btnAgregar = view.findViewById<MaterialButton>(R.id.btnAgregar)

        return view;
    }

    companion object {
        fun newInstance() : Fragment{
            val reminderFragment = ReminderFragment()
            return reminderFragment
        }
    }
}