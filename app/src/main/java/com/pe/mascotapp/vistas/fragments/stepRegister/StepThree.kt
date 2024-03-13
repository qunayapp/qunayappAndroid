package com.pe.mascotapp.vistas.fragments.stepRegister

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.OnEditTextChanged
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import java.util.*

class StepThree:Fragment() {

    lateinit var onEditTextChanged: OnEditTextChanged;

    var edtFecha: TextInputEditText?= null
    var txtStepTitle : TextView ?= null
    var imgPet: ImageView?= null
    var edtPeso: TextInputLayout?= null
    private val pickImage = 100
    private var imageUri: Uri?= null
    var autoRaza: AutoCompleteTextView?= null
    var radioGroup: RadioGroup? = null
    var radioButton: RadioButton?= null
    var edtCompartir:Button ?= null

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
        var view: View = inflater.inflate(R.layout.fragment_register_three, container,false)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona tu fecha de nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        edtFecha = view.findViewById<TextInputEditText>(R.id.edtFecha)
        edtPeso = view.findViewById<TextInputLayout>(R.id.edtPeso)
        imgPet = view.findViewById<ImageView>(R.id.imgPet)
        edtCompartir = view.findViewById<Button>(R.id.edtCompartir)
        txtStepTitle = view.findViewById<TextView>(R.id.txtStepTitle)

        arguments?.getString("title", "")?.let {
            txtStepTitle!!.text = it
        }
        edtFecha!!.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager,datePicker.toString())
        }
        val carosuelRegisterActivity = activity as CarosuelRegisterActivity

        edtCompartir!!.setOnClickListener {

            sendMessage("Si eres un familiar de '" +  carosuelRegisterActivity.edtNombreTwo.toString() + "', por favor registrate en mascotaapp (playstore)")
        }

        radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)


        radioGroup!!.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val rb = view.findViewById(checkedId) as RadioButton
            onEditTextChanged.onTextChanged(rb.text.toString(),3,"radioGroup")
        }

        edtPeso!!.editText!!.doOnTextChanged { text, start, before, count ->
            Utils.dump(text.toString())
            onEditTextChanged.onTextChanged(text.toString(),3,"edtPeso")
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

    fun updateImage(){
        val carosuelRegisterActivity = activity as CarosuelRegisterActivity
        Utils.dump(carosuelRegisterActivity.imgTwoSelected.toString())
        imgPet!!.setImageURI(carosuelRegisterActivity.imgTwoSelected)

        Utils.dump(carosuelRegisterActivity.edtNombreTwo.toString())

        //edtCompartir!!.text = "Compartir con otro familiar de " + carosuelRegisterActivity.edtNombreTwo.toString()
    }

    fun sendMessage(message:String){

        // Creating intent with action send
        val intent = Intent(Intent.ACTION_SEND)

        // Setting Intent type
        intent.type = "text/plain"

        // Setting whatsapp package name
        intent.setPackage("com.whatsapp")

        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT, message)

        // Checking whether whatsapp is installed or not
        if (intent.resolveActivity(context!!.packageManager) == null) {
            Toast.makeText(context,
                "Porfavor instalar whatsapp",
                Toast.LENGTH_SHORT).show()
            return
        }

        // Starting Whatsapp
        startActivity(intent)
    }

    companion object {
        fun newInstance(text: String) : StepThree{
            val stepThree = StepThree()
            val args = Bundle()
            args.putString("title", text)
            stepThree.arguments = args
            return stepThree
        }
    }
}