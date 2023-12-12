package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.pe.mascotapp.R
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.fragments.CarosuelFragmentTutorialState

class CarosuelTutorialActivity : AppCompatActivity() {

    var viewPStep: ViewPager? = null
    var btnVolver: TextView? = null
    var btnSiguiente: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carosuel_tutorial)

        viewPStep = findViewById<ViewPager>(R.id.viewPStep)
        btnVolver = findViewById<TextView>(R.id.btnVolver)
        btnSiguiente = findViewById<MaterialButton>(R.id.btnSiguiente)
        viewPStep!!.adapter = CarosuelFragmentTutorialState(supportFragmentManager,this)

        btnVolver!!.setOnClickListener{
            finishStep()
        }

        btnSiguiente!!.setOnClickListener {
            nextStep()
        }

        viewPStep!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Utils.dump("posicion por scrolear onPageSelected " + position)

                if(position == 4){

                    btnSiguiente!!.setOnClickListener {
                        /*val str1 = "INSERT INTO usuario(name, birthdate, email,pass, numPhone, img, sex ) VALUES\n"+
                                "('"+ edtNombreOne +"','"+ edtFechaOne +"','"+ usuario!!.email +"','"+ usuario!!.pass +"','"+ usuario!!.numPhone +"','"+ imgOne +"','"+ radioGroupOne+"')"
                        val idUsuario = presentador.insertar(str1)

                        val str2 = "INSERT INTO mascota(name, tipo, raza,weight, birthdate, img, sex) VALUES\n"+
                                "('"+ edtNombreTwo +"','"+ radioGroupTwo +"','"+ edtRazaTwo +"','"+ edtPesoThree +"','"+ edtFechaThree +"','"+ imgTwo +"','"+ radioGroupThree+"')"
                        val idMascota = presentador.insertar(str2)

                        val str3 = "INSERT INTO mascotaUsuario(idUsuario, idMascota) VALUES\n"+
                                "("+ idUsuario +","+ idMascota +")"
                        val idMascotaUsuario = presentador.insertar(str3)

                        val preferences = getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE)
                        with (preferences.edit()) {
                            putBoolean(Constantes.SHARED_PREF_SUCCESS, true)
                            putString(Constantes.SHARED_PREF_MESSAGE, "logeado")
                            putInt(Constantes.SHARED_ID_USUARIO, idUsuario)
                            commit()
                        }*/

                        //showDialog()

                        finishStep()
                    }
                    btnVolver!!.setOnClickListener {
                        backStep()
                    }
                }else{
                    btnSiguiente!!.setOnClickListener {
                        nextStep()
                    }
                    btnVolver!!.setOnClickListener {
                        backStep()
                    }
                }

            }

        })

    }
    private fun backStep(){
        viewPStep!!.setCurrentItem(getItem(-1), true)
    }
    private fun finishStep(){
        finishActivity()
    }

    private fun getItem(i:Int):Int{
        Utils.dump("posicion" + viewPStep?.currentItem)
        if(viewPStep?.currentItem == 4) {
            btnSiguiente!!.setOnClickListener {
                finishStep()
            }
        }else if(viewPStep?.currentItem == 0) {

            btnVolver!!.setOnClickListener {
                finishStep()
            }
            btnSiguiente!!.setOnClickListener {
                nextStep()
            }
        }else{
            btnSiguiente!!.setOnClickListener {
                nextStep()
            }
            btnVolver!!.setOnClickListener {
                backStep()
            }
        }
        return viewPStep!!.currentItem + i
    }

    private fun nextStep(){
        viewPStep!!.setCurrentItem(getItem(+1), true)
    }

    private fun finishActivity(){

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

        /*when(vista){
            0 -> {
                val intent = Intent(this, HomeStartActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }*/


    }
}