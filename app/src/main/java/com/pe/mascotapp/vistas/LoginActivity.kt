package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.PrincipalPresentador
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils

class LoginActivity : AppCompatActivity() {

    var edtEmail: TextInputLayout ?=null
    var edtPassword: TextInputLayout ?=null
    var btnIngresar:Button ?= null

    private lateinit var presentador: PrincipalPresentador.VistaStart

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presentador = PrincipalPresentador.VistaStart(this)

        edtEmail = findViewById<TextInputLayout>(R.id.edtEmail)
        edtPassword = findViewById<TextInputLayout>(R.id.edtPassword)
        btnIngresar = findViewById<Button>(R.id.btnIngresar)

        btnIngresar!!.setOnClickListener {
            if (validarInputs()){

                val str = "SELECT * FROM usuario WHERE email= '"+ edtEmail!!.editText!!.text.trim() + "'"
                val data = presentador.leer(str)
                val count = data.count
                Utils.dump("cantidad:" + count)

                if (count != 0){
                    if (data.moveToFirst()){
                        do {
                            val pass = data.getString(data.getColumnIndex("pass"))
                            val id = data.getString(data.getColumnIndex("id"))
                            Utils.dump(pass)
                            Utils.dump(id)
                            Utils.dump(edtPassword!!.editText!!.text.toString())

                            if (pass == edtPassword!!.editText!!.text.toString()){

                                val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
                                with (preferences.edit()) {
                                    putBoolean(Constantes.SHARED_PREF_SUCCESS, true)
                                    putString(Constantes.SHARED_PREF_MESSAGE, "logeado")
                                    putInt(Constantes.SHARED_ID_USUARIO, id.toInt())
                                    commit()
                                }

                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this,"Las credenciales son incorrectas",Toast.LENGTH_LONG).show()
                            }

                        }while (data.moveToNext())
                    }
                }else{
                    Toast.makeText(this,"Usuario no registrado",Toast.LENGTH_LONG).show()
                }




            }

        }
    }

    fun validarInputs(): Boolean{
        if (edtEmail!!.editText!!.text.trim().length == 0){
            Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_LONG).show()
            return false
        }

        if (edtPassword!!.editText!!.text.trim().length == 0){
            Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}