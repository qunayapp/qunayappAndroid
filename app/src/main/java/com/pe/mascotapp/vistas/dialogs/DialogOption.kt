package com.pe.mascotapp.vistas.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.DialogOptionBinding
import com.pe.mascotapp.vistas.adapters.ContainerOptionsAdapter
import com.pe.mascotapp.vistas.adapters.OptionFieldAdapter
import com.pe.mascotapp.vistas.adapters.OptionViewInterface

class DialogOption : DialogFragment() {

    private var listOptions = listOf<OptionViewInterface>()

    private var canAddOtherContainer: Boolean = false

    private var callBackOptions: () -> Unit = {}

    private var listListOptions = arrayListOf<List<OptionViewInterface>>()

    private var listOptionsFieldAdapter = mutableListOf(
        OptionFieldAdapter(listOptions)
    )

    fun setListOptions(listOptions: List<OptionViewInterface>) {
        this.listOptions = listOptions
        this.listOptionsFieldAdapter = mutableListOf(OptionFieldAdapter(listOptions))
    }

    fun setListListOptions(listListOptions: ArrayList<List<OptionViewInterface>>) {
        this.listListOptions = listListOptions
        this.listOptions = listListOptions.first()
        this.listOptionsFieldAdapter = mutableListOf(OptionFieldAdapter(listOptions))
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DialogOptionBinding.inflate(inflater, container, false)
        binding.rvRvOptions.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvRvOptions.adapter = ContainerOptionsAdapter(
            listOptionsFieldAdapter = listOptionsFieldAdapter, canAddOtherContainer = canAddOtherContainer
        ) {
            val tempOptions = listOptions.map {
                val copyOption = it.copyOption()
                copyOption.isSelected = false
                copyOption
            }
            listOptionsFieldAdapter.add(OptionFieldAdapter(tempOptions))
            listListOptions.add(tempOptions)
            binding.rvRvOptions.adapter?.notifyItemInserted(listOptionsFieldAdapter.size)
        }
        binding.tvAccept.setOnClickListener {
            callBackOptions()
            this.dismiss()
        }
        return binding.root
    }

}