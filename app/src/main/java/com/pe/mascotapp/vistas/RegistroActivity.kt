package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Usuario
import com.pe.mascotapp.utils.Utils

class RegistroActivity : AppCompatActivity() {


    var btnVolver: TextView?= null
    var btnRegistrar: Button?= null
    var edtEmail: TextInputLayout?= null
    var edtPassword: TextInputLayout?= null
    var edtPassword2: TextInputLayout?= null
    var edtNumeroCelular: TextInputLayout?= null
    var usuario:Usuario = Usuario()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnVolver = findViewById<View>(R.id.btnVolver) as Button
        btnRegistrar = findViewById<View>(R.id.btnRegistrar) as Button

        edtEmail = findViewById<View>(R.id.edtEmail) as TextInputLayout
        edtPassword = findViewById<View>(R.id.edtPassword) as TextInputLayout
        edtPassword2 = findViewById<View>(R.id.edtPassword2) as TextInputLayout
        edtNumeroCelular = findViewById<View>(R.id.edtNumeroCelular) as TextInputLayout

        btnVolver!!.setOnClickListener {
            onBackPressed()
        }

        btnRegistrar!!.setOnClickListener {

            if(validateForm()){

                usuario.email = edtEmail?.editText!!.text.toString().trim()
                usuario.pass = edtPassword?.editText!!.text.toString().trim()
                usuario.numPhone = edtNumeroCelular?.editText!!.text.toString().trim()

                val intent = Intent(this, CarosuelRegisterActivity::class.java)
                intent.putExtra("OBJECT_USUARIO",usuario)
                startActivity(intent)
            }
        }
    }

    fun validateForm():Boolean {
        if(!Utils.isValidEmail(edtEmail?.editText!!.text.toString().trim())){
            Toast.makeText(this,"Ingrese un correo valido",Toast.LENGTH_LONG).show()
            return false
        }

        if(edtPassword?.editText!!.text.toString().length == 0){
            Toast.makeText(this,"Ingrese un contraseña",Toast.LENGTH_LONG).show()
            return false
        }

        if(edtPassword2?.editText!!.text.toString().length == 0){
            Toast.makeText(this,"Repita la contraseña",Toast.LENGTH_LONG).show()
            return false
        }


        if(!(edtPassword?.editText!!.text.toString() == edtPassword2?.editText!!.text.toString())){
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show()
            return false
        }

        if(edtNumeroCelular?.editText!!.text.toString().length == 0){
            Toast.makeText(this,"Ingrese un número de celular",Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}