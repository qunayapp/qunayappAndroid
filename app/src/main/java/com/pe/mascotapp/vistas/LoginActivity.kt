package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.interfaces.PrincipalPresentador
import com.pe.mascotapp.interfaces.RetrofitServiceApp
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.fragments.stepRegister.CustomTextField
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity() {

    var edtEmail: TextInputLayout ?=null
    var edtPassword: TextInputLayout ?=null
    var btnIngresar:Button ?= null

    private lateinit var presentador: PrincipalPresentador.VistaStart

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
        ; // (for Android Built In Theme)

        /*        presentador = PrincipalPresentador.VistaStart(this)
                edtEmail = findViewById<TextInputLayout>(R.id.edtEmail)
                edtPassword = findViewById<TextInputLayout>(R.id.edtPassword)
                btnIngresar = findViewById<Button>(R.id.btnIngresar)

                btnIngresar!!.setOnClickListener {
                    if (validarInputs()){

                    }*/
        }

/*    @SuppressLint("SuspiciousIndentation")
    fun getUser() {

        val correo = edtEmail!!.editText!!.text.trim()
        val pass = edtPassword!!.editText!!.text.trim()

        Utils.dump(getSHA(pass.toString()))
        *//*6948e242200a25a5ea5c2fbbadc61f623436ea41f8a37138e4a103a438e10121*//*
        RetrofitServiceApp().getLoginUser("'" + correo.toString() + "'",
            "'" + getSHA(pass.toString()) +"'" ){
            Toast.makeText(this, "Ingreso", Toast.LENGTH_LONG).show()
            Utils.dump("INGRESO CON EL SIGUIENTE JSON: " + it)

            if (it!!.idUsuario != 0){
                val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
                with (preferences.edit()) {
                    putBoolean(Constantes.SHARED_PREF_SUCCESS, true)
                    putString(Constantes.SHARED_PREF_MESSAGE, "logeado")
                    putInt(Constantes.SHARED_ID_USUARIO, it!!.idUsuario)
                    commit()
                }

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Contraseña o usuario invalido", Toast.LENGTH_LONG).show()
            }

        }

        *//*Thread({
            runOnUiThread {
                try {
                    val call = RetrofitServiceApp().getRetrofit().create(Servicios::class.java)
                        .getLoginUser(
                            "prueba@prueba.com",
                            "6948e242200a25a5ea5c2fbbadc61f623436ea41f8a37138e4a103a438e10121"
                        ).execute()

                    val puppies = call.body() as SesionUsuario
                    if (puppies.correo.length > 0) {
                        Toast.makeText(this, "Ingreso", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "No", Toast.LENGTH_LONG).show()
                    }

                }catch (e:Exception){
                    Utils.dump(e.toString())
                }

            }
        }).start()*//*

    }*/

    /*fun getLoginUser(correo:String, encriptado:String) {
        RetrofitServiceApp().getRetrofit().create(Servicios::class.java).getLoginUser(correo,encriptado).enqueue(object :
            retrofit2.Callback<SesionUsuario> {
            override fun onResponse(call: Call<SesionUsuario>, response: Response<SesionUsuario>) {
                // Procesar respuesta exitosa
                Utils.dump(response.body().toString())
            }

            override fun onFailure(call: Call<SesionUsuario>, t: Throwable) {
                // Procesar error en la petición
                Utils.dump(t.message.toString())
            }

        })
    }*/

    fun getSHA(input: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            val messageDigest = md.digest(input.toByteArray())
            val num = BigInteger(1, messageDigest)
            var hashText = num.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }
            return hashText
        } catch (ex: NoSuchAlgorithmException) {
            Utils.dump("Exception Occured: ${ex.message}")
            return ""
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

@Preview
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }

    fun validateInputs() :Boolean {
        return (user.isNotBlank() && password.isNotBlank()).apply {
            if (!this){
                validationMessage = when {
                    user.isBlank() -> "Ingresa tu nombre"
                    password.isBlank() -> "Ingresa tu email"
                    else -> "" // All valid
                }
            }
        }
    }
    fun getSHA(input: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            val messageDigest = md.digest(input.toByteArray())
            val num = BigInteger(1, messageDigest)
            var hashText = num.toString(16)
            while (hashText.length < 32) {
                hashText = "0$hashText"
            }
            return hashText
        } catch (ex: NoSuchAlgorithmException) {
            Utils.dump("Exception Occured: ${ex.message}")
            return ""
        }
    }
    Image(
        painter = painterResource(id = R.drawable.background_main_screen),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_qunay),
                contentDescription = null,
                modifier = Modifier.size(220.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(modifier = Modifier.padding( horizontal = 16.dp)) {
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = painterResource(id = R.drawable.nombre_usuario),
                    value = user,
                    onValueChange = {
                        user = it
                    },
                    label = "Nombre ",
                )
                Spacer(
                    modifier = Modifier.height(
                        22.dp
                    )
                )
                CustomTextField(
                    Modifier.fillMaxWidth(),
                    leadingIcon = painterResource(id = R.drawable.candado),
                    value = password,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    onValueChange = {
                        password = it
                    },
                    label = stringResource(id = R.string.label_password),
                    trailingIcon = {
                        Icon(
                            painter = if (passwordVisible) painterResource(id = R.drawable.password_show) else painterResource(id = R.drawable.password_hide),
                            contentDescription = null,
                            modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                        )
                    },
                )
            }
            Spacer(
                modifier = Modifier.height(
                    22.dp
                )
            )
            Button(
                onClick = {
                    if (!validateInputs()) {
                        Toast.makeText(
                            context,
                            validationMessage,Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                        Log.d("TAG", "validateInputs: " + validationMessage)
                    }else {
                        RetrofitServiceApp().getLoginUser(
                            "'" + user.toString() + "'",
                            "'" + getSHA(password.toString()) + "'"
                        ) {
                            Toast.makeText(context, "Ingreso", Toast.LENGTH_LONG).show()
                            Utils.dump("INGRESO CON EL SIGUIENTE JSON: " + it)

                            if (it!!.idUsuario != 0) {
                                val preferences = context.getSharedPreferences(
                                    Constantes.SHARED_PREF,
                                    Context.MODE_PRIVATE
                                )
                                with(preferences.edit()) {
                                    putBoolean(
                                        com.pe.mascotapp.utils.Constantes.SHARED_PREF_SUCCESS,
                                        true
                                    )
                                    putString(
                                        com.pe.mascotapp.utils.Constantes.SHARED_PREF_MESSAGE,
                                        "logeado"
                                    )
                                    putInt(
                                        com.pe.mascotapp.utils.Constantes.SHARED_ID_USUARIO,
                                        it!!.idUsuario
                                    )
                                    commit()
                                }

                                val intent = Intent(context, HomeActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Contraseña o usuario invalido",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                    }
                },
                modifier = Modifier
                    .padding(18.dp)
                    .width(240.dp).height(50.dp),
                shape = RoundedCornerShape(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue_primary),
                )
            ) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.login),
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(
                            R.font.worksans_regular
                        )
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Volver",
                    color = colorPrimary,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.worksans_regular
                        )
                    ),
                    modifier = Modifier.clickable {
                        (context as? LoginActivity)?.onBackPressed()
                    }
                )
            }


        }
    }
}