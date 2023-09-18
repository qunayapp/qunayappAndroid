package com.pe.mascotapp.vistas

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.utils.Utils
import java.util.*

class RegisterStepOneActivity : AppCompatActivity() {

    var edtFecha: TextInputEditText?= null
    var imgPersona: ImageView?= null
    var txtAgregarFoto: TextView?= null
    var edtNombre: TextInputLayout?= null
    private val pickImage = 100
    private var imageUri: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_step_one)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona tu fecha de nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        edtFecha = findViewById<TextInputEditText>(R.id.edtFecha)
        imgPersona = findViewById<ImageView>(R.id.imgPersona)
        txtAgregarFoto = findViewById<View>(R.id.txtAgregarFoto) as TextView
        edtNombre = findViewById<TextInputLayout>(R.id.edtNombre)

        imgPersona!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        txtAgregarFoto!!.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)
        }

        edtFecha!!.setOnClickListener {
            datePicker.show(supportFragmentManager,datePicker.toString())
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

            edtFecha!!.setText("" + dia + "/" + mes + "/" + calendar.get(Calendar.YEAR))
            //fechaNacimiento = "" + dia + "/" + mes + "/" + calendar.get(Calendar.YEAR)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            imgPersona!!.setImageURI(imageUri)
        }
    }
}