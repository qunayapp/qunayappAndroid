package com.pe.mascotapp.vistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R

class ReminderActivity : AppCompatActivity() {

    var edtNombreEvento: TextInputLayout?= null
    var btnAceptar: MaterialButton?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        edtNombreEvento = findViewById<TextInputLayout>(R.id.edtNombreEvento)
        btnAceptar = findViewById<MaterialButton>(R.id.btnAceptar)

        btnAceptar!!.setOnClickListener {
            
        }

    }
}