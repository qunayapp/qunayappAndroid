package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.pe.mascotapp.R

class MainActivity : AppCompatActivity() {

    var txtLogin:TextView ?= null
    var btnRegistrar:Button ?= null
    var txtlogin:TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtLogin = findViewById<View>(R.id.txtlogin) as TextView
        btnRegistrar = findViewById<View>(R.id.btnRegistrar) as Button
        txtlogin = findViewById<TextView>(R.id.txtlogin)

        btnRegistrar!!.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        txtlogin!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}