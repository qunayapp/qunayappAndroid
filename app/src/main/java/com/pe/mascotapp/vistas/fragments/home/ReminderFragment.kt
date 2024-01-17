package com.pe.mascotapp.vistas.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R

class ReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_reminderfragment, container,false)

        return view;
    }

    companion object {
        fun newInstance() : Fragment{
            val reminderFragment = ReminderFragment()
            return reminderFragment
        }
    }
}