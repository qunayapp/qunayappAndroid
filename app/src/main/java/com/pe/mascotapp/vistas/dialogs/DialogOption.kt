package com.pe.mascotapp.vistas.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pe.mascotapp.databinding.DialogOptionBinding

class DialogOption : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DialogOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

}