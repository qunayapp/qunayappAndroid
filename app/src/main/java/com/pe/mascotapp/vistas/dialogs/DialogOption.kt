package com.pe.mascotapp.vistas.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.DialogOptionBinding
import com.pe.mascotapp.vistas.adapters.OptionFieldAdapter
import com.pe.mascotapp.vistas.adapters.OptionViewInterface

class DialogOption : DialogFragment() {
    private var listOptions = listOf<OptionViewInterface>()

    private var canAddOtherContainer: Boolean = false

    private var callBackOptions: () -> Unit = {}

    fun setListOptions(listOptions: List<OptionViewInterface>) {
        this.listOptions = listOptions
    }

    fun setCanAddOtherContainer(canAddOtherContainer: Boolean) {
        this.canAddOtherContainer = canAddOtherContainer
    }

    fun setCallBackOptions(callBackOptions: () -> Unit) {
        this.callBackOptions = callBackOptions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = DialogOptionBinding.inflate(inflater, container, false)
        binding.rvRvOptions.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvRvOptions.adapter = OptionFieldAdapter(listOptions)
        binding.tvAccept.setOnClickListener {
            callBackOptions()
            this.dismiss()
        }
        return binding.root
    }
}
