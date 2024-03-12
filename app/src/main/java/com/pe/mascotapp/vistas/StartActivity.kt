package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.pe.mascotapp.R
import com.pe.mascotapp.interfaces.PrincipalPresentador
import com.pe.mascotapp.interfaces.Servicios
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils

class StartActivity : AppCompatActivity() {

    var servicios: Servicios? = null
    var imgLogoSplash: ImageView? = null
    val imagenesSplash = arrayOf(R.drawable.q_verde, R.drawable.logo_verde)

    private lateinit var presentador: PrincipalPresentador.VistaStart

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        presentador = PrincipalPresentador.VistaStart(this)
        start()
    }

    fun validateUser() {
        Utils.dump("Validando usuario")
        val pref = applicationContext.getSharedPreferences(
            Constantes.SHARED_PREF,
            Context.MODE_PRIVATE
        )
        val session = pref.getBoolean(Constantes.SHARED_PREF_SUCCESS, false)
        if (session) {
            val mensaje = pref.getString(Constantes.SHARED_PREF_MESSAGE, "")
            Utils.dump("client_name: $mensaje")
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        /*val intent = Intent(this, CarouselActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)*/

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("Range")
    fun start() {
        imgLogoSplash = findViewById<View>(R.id.imgLogoSplash) as ImageView
        animacionLogo(imgLogoSplash!!)

        if (presentador.startDatabe(this)) {
            val str = "SELECT * FROM usuario"
            val data = presentador.leer(str)
            val count = data.count
            Utils.dump("cantidad:" + count)
            if (data.moveToFirst()) {
                do {
                    val dataDes = data.getString(data.getColumnIndex("name"))
                    Utils.dump(dataDes)
                } while (data.moveToNext())
            }
            val str1 = "SELECT * FROM especies"
            val dataEspecies = presentador.leer(str1)
            val countEspecies = dataEspecies.count
            Utils.dump("cantidad:" + countEspecies)
            if (dataEspecies.moveToFirst()) {
                do {
                    val dataDesEspecies = dataEspecies.getString(dataEspecies.getColumnIndex("name"))
                    Utils.dump(dataDesEspecies)
                } while (dataEspecies.moveToNext())
            } else {
                Utils.dump("Entro crear data")
                getAllComponentesApp()
            }


            Handler(Looper.getMainLooper()).postDelayed({
                validateUser()
            }, 5000)


        } else {
            Toast.makeText(this, "No se pudo generar la base de datos interna", Toast.LENGTH_LONG).show()
        }

    }

    fun getAllComponentesApp() {

        val str: String = "INSERT INTO especies (name, description) VALUES\n" +
                "('" + "Perro" + "','" + "Perro mascota" + "'),\n" +
                "('" + "Gato" + "','" + "Gato mascota" + "'),\n" +
                "('" + "Otros" + "','" + "Conejo mascota" + "')"

        presentador.insertar(str)

        val str1: String = "INSERT INTO razas (idEspecie, name, description) VALUES\n" +
                "(" + 1 + ",'Affenpinscher'" + ",'" + "especie de perro" + "'),\n" +
                "(" + 1 + ",'Afghan Hound'" + ",'" + "especie de perro" + "'),\n" +
                "(" + 1 + ",'Golden'" + ",'" + "especie de perro" + "'),\n" +
                "(" + 2 + ",'Abyssinian'" + ",'" + "Gato mascota" + "'),\n" +
                "(" + 2 + ",'American Curl'" + ",'" + "Gato mascota" + "'),\n" +
                "(" + 2 + ",'Balinese'" + ",'" + "Gato mascota" + "'),\n" +
                "(" + 2 + ",'York Chocolate'" + ",'" + "Gato mascota" + "'),\n" +
                "(" + 3 + ",'Otros'" + ",'" + "Conejo mascota" + "')"

        presentador.insertar(str1)


    }

    private fun animacionLogo(img: ImageView) {
        val animation1 = AlphaAnimation(1.0f, 0f)
        animation1.duration = 2500
        animation1.startOffset = 0
        animation1.fillAfter = true
        img.startAnimation(animation1)

        Handler(Looper.getMainLooper()).postDelayed({
            img.setImageResource(imagenesSplash[0])
            val animation2 = AlphaAnimation(0f, 1f)
            animation2.duration = 2500
            animation2.startOffset = 0
            animation2.fillAfter = true
            img.layoutParams.width = 500
            img.layoutParams.height = 600
            img.startAnimation(animation2)
        }, 2500)
    }
}