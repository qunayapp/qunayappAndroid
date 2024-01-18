package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.notifications.NotificationService


class ReminderActivity : AppCompatActivity() {

    var edtNombreEvento: TextInputLayout?= null
    var btnAceptar: MaterialButton?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        edtNombreEvento = findViewById<TextInputLayout>(R.id.edtNombreEvento)
        btnAceptar = findViewById<MaterialButton>(R.id.btnAceptar)

        btnAceptar!!.setOnClickListener {

            startService()
            
        }

    }

    fun startService() {
        val input: String = "hola"
        val serviceIntent = Intent(this, NotificationService::class.java)
        serviceIntent.putExtra("inputExtra", input)
        ContextCompat.startForegroundService(this, serviceIntent)
    }
    fun stopService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        stopService(serviceIntent)
    }
}