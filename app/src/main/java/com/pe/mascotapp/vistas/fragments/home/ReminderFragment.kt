package com.pe.mascotapp.vistas.fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import com.pe.mascotapp.vistas.ReminderActivity

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

        btnAgregar!!.setOnClickListener {
            val intent = Intent(context, ReminderActivity::class.java)
            startActivity(intent)
        }

        return view;
    }

    companion object {
        fun newInstance() : Fragment{
            val reminderFragment = ReminderFragment()
            return reminderFragment
        }
    }
}