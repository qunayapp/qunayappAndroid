package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Paint.Align
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.OnEditTextChanged
import com.pe.mascotapp.modelos.Raza
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.ListSelectedActivity
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


class StepTwo:Fragment() {

    lateinit var onEditTextChanged: OnEditTextChanged;
    var imgPet: ImageView?= null
    var txtAgregarFoto:TextView ?= null
    var txtStepTitle : TextView ?= null
    var edtNombre: TextInputLayout?= null
    var edtRaza:TextInputLayout ?= null
    var edtTextRaza:TextInputEditText ?= null
    var lnlDynamically:LinearLayout ?= null
    private val pickImage = 100
    private val razaSelected = 200
    private var imageUri: Uri?= null
    //var autoRaza:AutoCompleteTextView?= null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton?= null
    var edtFecha: TextInputEditText?= null

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
        var view: View = inflater.inflate(R.layout.fragment_register_two, container,false)
        imgPet = view.findViewById<ImageView>(R.id.imgPet)
        txtAgregarFoto = view.findViewById<TextView>(R.id.txtAgregarFoto)
        //autoRaza = view.findViewById<AutoCompleteTextView>(R.id.autoRaza)
        edtFecha = view.findViewById<TextInputEditText>(R.id.edtFecha)
        edtRaza = view.findViewById<TextInputLayout>(R.id.edtRaza)
        edtNombre = view.findViewById<TextInputLayout>(R.id.edtNombre)
        edtTextRaza = view.findViewById<TextInputEditText>(R.id.edtTextRaza)
        lnlDynamically = view.findViewById<LinearLayout>(R.id.lnlDynamically)
        txtStepTitle = view.findViewById<TextView>(R.id.txtStepTitle)

        arguments?.getString("title", "")?.let {
            txtStepTitle!!.text = it
        }
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona su fecha de nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        edtFecha!!.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager,datePicker.toString())
        }
        radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup!!.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = view.findViewById(checkedId) as RadioButton
            //rb.setTextColor(context?.let { ContextCompat.getColorStateList(it, R.color.verdeq) })
            onEditTextChanged.onTextChanged(rb.text.toString(),2,"radioGroup")
        }

        imgPet!!.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(pickImage)
            //val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //startActivityForResult(gallery,pickImage)
        }

        txtAgregarFoto!!.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(pickImage)
            //val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            //startActivityForResult(gallery,pickImage)
        }

        /*var razasArrayOf = arrayListOf<String>()
        razasArrayOf.add("golden")
        razasArrayOf.add("gran danes")
        razasArrayOf.add("pequines")


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, razasArrayOf
        )*/
        /*autoRaza?.setAdapter(adapter)
        autoRaza?.threshold = 1

        autoRaza?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            //categeoriaSelected = position
            //categeoria = selectedItem
            Utils.dump("categeoria selectedItem: $selectedItem")

            onEditTextChanged.onTextChanged(selectedItem,2,"autoRaza")

            /*for (i in 0 .. paisesArray.size - 1) {
                if (paisesArray.get(i).valor.equals(selectedItem)) {
                    paisesArraySelected = paisesArray.get(i).id
                    break
                }
            }*/
        }*/

        edtTextRaza!!.setOnClickListener {
            val intent = Intent(activity, ListSelectedActivity::class.java)
            startActivityForResult(intent, razaSelected);
            //startActivity(intent)
        }

        edtNombre!!.editText!!.doOnTextChanged { text, start, before, count ->
            Utils.dump(text.toString())
            onEditTextChanged.onTextChanged(text.toString(),2,"edtNombre")
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
            onEditTextChanged.onTextChanged(txtFecha,3,"edtFecha")

        }


        return view;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            imgPet!!.setImageURI(imageUri)
            onEditTextChanged.onImageChange(2,imageUri!!)

        }

        if (resultCode == Activity.RESULT_OK && requestCode == razaSelected){

            val result = data?.getSerializableExtra("selected") as ArrayList<Raza>
            lnlDynamically!!.removeAllViews()

            var selected = ""
            for (item in result){

                if(result.last().id === item.id){
                    selected += item.nombre
                }else{
                    selected += item.nombre + ";"
                }

                val child = LayoutInflater.from(context).inflate(R.layout.raza_selected_holder,null)
                val txtRazaSelected = child.findViewById<TextView>(R.id.txtRazaSelected)
                txtRazaSelected.text = item.nombre
                lnlDynamically!!.addView(child)
            }
            onEditTextChanged.onTextChanged(selected,2,"autoRaza")
        }
    }


    companion object {
        fun newInstance(text: String) : StepTwo {
            val stepTwo = StepTwo()
            val args = Bundle()
            args.putString("title", text)
            stepTwo.arguments = args
            return stepTwo
        }
    }

}