package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.OnEditTextChanged
import com.pe.mascotapp.utils.Utils
import java.util.*

import android.widget.RadioButton

import android.widget.RadioGroup
import com.github.dhaval2404.imagepicker.ImagePicker


class StepOne():Fragment() {

    lateinit var onEditTextChanged:OnEditTextChanged;
    var edtFecha: TextInputEditText?= null
    var imgPersona:ImageView ?= null
    var txtAgregarFoto:TextView ?= null
    var edtNombre: TextInputLayout?= null
    private val pickImage = 100
    private var imageUri:Uri ?= null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onEditTextChanged = context as OnEditTextChanged
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var view: View = inflater.inflate(R.layout.fragment_register_one, container,false)
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona tu fecha de nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        edtFecha = view.findViewById<TextInputEditText>(R.id.edtFecha)
        imgPersona = view.findViewById<ImageView>(R.id.imgPersona)
        txtAgregarFoto = view.findViewById<View>(R.id.txtAgregarFoto) as TextView
        edtNombre = view.findViewById<TextInputLayout>(R.id.edtNombre)

        radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup!!.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = view.findViewById(checkedId) as RadioButton
            onEditTextChanged.onTextChanged(rb.text.toString(),1,"radioGroup")
        }

        imgPersona!!.setOnClickListener {

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(pickImage)

            //val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //startActivityForResult(gallery,pickImage)
        }

        txtAgregarFoto!!.setOnClickListener {
            //val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //startActivityForResult(gallery,pickImage)
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(pickImage)
        }

        edtFecha!!.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager,datePicker.toString())
        }

        edtNombre!!.editText!!.doOnTextChanged { text, start, before, count ->
            Utils.dump(text.toString())
            onEditTextChanged.onTextChanged(text.toString(),1,"edtNombre")
        }

        datePicker.addOnPositiveButtonClickListener {

            Utils.dump(datePicker.headerText)

            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            //textView.text = "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
            //        "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"

            var mes = ""
            if ((calendar.get(Calendar.MONTH)+1) < 10){
                mes = "0" + (calendar.get(Calendar.MONTH) + 1)
            }else{
                mes = (calendar.get(Calendar.MONTH) + 1 ).toString()
            }

            var dia = "00";
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10){
                dia = "0" + calendar.get(Calendar.DAY_OF_MONTH)
            }else{
                dia = calendar.get(Calendar.DAY_OF_MONTH).toString()
            }

            Utils.dump("" + dia + " - " + mes + " - " + calendar.get(Calendar.YEAR))

            val txtFecha = "" + dia + "/" + mes + "/" + calendar.get(Calendar.YEAR)
            edtFecha!!.setText(txtFecha)
            //fechaNacimiento = "" + dia + "/" + mes + "/" + calendar.get(Calendar.YEAR)
            onEditTextChanged.onTextChanged(txtFecha,1,"edtFecha")

        }


        return view;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Utils.dump(requestCode.toString())

        if (resultCode == RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            imgPersona!!.setImageURI(imageUri)
            onEditTextChanged.onImageChange(1,imageUri!!)
        }
    }

    companion object {
        fun newInstance() : StepOne {
            val stepOne = StepOne()
            return stepOne
        }
    }
}