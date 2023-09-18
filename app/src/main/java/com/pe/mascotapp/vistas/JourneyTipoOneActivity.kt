package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.Veterinaria
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.adapters.HomeAdapter
import com.pe.mascotapp.vistas.adapters.VeterinariaAdapter

class JourneyTipoOneActivity : AppCompatActivity() {

    var rcvVeterinarias:RecyclerView ?= null
    var veterinariaArray:ArrayList<Veterinaria> = ArrayList()
    var veterinariaAdapter:VeterinariaAdapter ?= null
    var autoDistrito: AutoCompleteTextView ?= null
    var txtTitleFilter:TextView ?= null
    var veterinariaArraySelected: String = ""
    var veterinariaArrayFilter:ArrayList<Veterinaria> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_tipo_one)

        rcvVeterinarias = findViewById<View>(R.id.rcvVeterinarias) as RecyclerView
        autoDistrito = findViewById<View>(R.id.autoDistrito) as AutoCompleteTextView
        txtTitleFilter = findViewById<View>(R.id.txtTitleFilter) as TextView
        obtenerData()

        val distritosArrayOf = arrayListOf<String>()

        for (item in veterinariaArray){
            if(!distritosArrayOf.contains(item.distrito)){
                distritosArrayOf.add(item.distrito)
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, distritosArrayOf
        )
        autoDistrito?.setAdapter(adapter)
        autoDistrito?.threshold = 1

        autoDistrito?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            //categeoriaSelected = position
            //categeoria = selectedItem
            Utils.dump("categeoria selectedItem: $selectedItem")

            for (i in 0 .. veterinariaArray.size - 1) {
                if (veterinariaArray.get(i).distrito.equals(selectedItem)) {
                    veterinariaArraySelected = veterinariaArray.get(i).unbigeo
                    break
                }
            }
            val filter = veterinariaArray.filter { veterinaria -> veterinaria.unbigeo == veterinariaArraySelected }

            if(filter.size > 0){
                veterinariaArrayFilter.clear()
            }
            for (item in filter){
                veterinariaArrayFilter.add(item)
            }
            veterinariaAdapter!!.notifyDataSetChanged()

            txtTitleFilter!!.text = "Nuestros aliados"

        }

        val filter = veterinariaArray.filter { veterinaria -> veterinaria.favorito }

        for (item in filter){
            veterinariaArrayFilter.add(item)
        }

        startRCVHome()
    }

    fun obtenerData(){

        val fechasDisponibleArrayOf = arrayListOf<String>()
        fechasDisponibleArrayOf.add("2023-02-23")
        fechasDisponibleArrayOf.add("2023-02-24")
        fechasDisponibleArrayOf.add("2023-02-25")

        val tiempoDisponibleArrayOf = arrayListOf<String>()
        tiempoDisponibleArrayOf.add("12:00:00")
        tiempoDisponibleArrayOf.add("13:00:00")
        tiempoDisponibleArrayOf.add("14:00:00")

        val veterinaria1 = Veterinaria()
        veterinaria1.id = 0
        veterinaria1.titulo = "ChamacApp"
        veterinaria1.descripcion = "Veterinaria"
        veterinaria1.img = "icon_vet_uno"
        veterinaria1.unbigeo = "1010"
        veterinaria1.distrito = "Lince"
        veterinaria1.favorito = true
        veterinaria1.horarios.fechas = fechasDisponibleArrayOf
        veterinaria1.horarios.tiempo = tiempoDisponibleArrayOf

        veterinariaArray.add(veterinaria1)

        val veterinaria2 = Veterinaria()
        veterinaria2.id = 1
        veterinaria2.titulo = "Bruci"
        veterinaria2.descripcion = "Paseos"
        veterinaria2.img = "veterinaria_dos"
        veterinaria2.unbigeo = "1020"
        veterinaria2.distrito = "Surco"
        veterinaria2.favorito = true
        veterinaria2.horarios.fechas = fechasDisponibleArrayOf
        veterinaria2.horarios.tiempo = tiempoDisponibleArrayOf
        veterinariaArray.add(veterinaria2)


        val veterinaria3 = Veterinaria()
        veterinaria3.id = 2
        veterinaria3.titulo = "Mascotienda Charlie"
        veterinaria3.descripcion = "Veterinaria"
        veterinaria3.img = "veterinaria_tres"
        veterinaria3.unbigeo = "1030"
        veterinaria3.distrito = "Magdalena"
        veterinaria3.favorito = false
        veterinaria3.horarios.fechas = fechasDisponibleArrayOf
        veterinaria3.horarios.tiempo = tiempoDisponibleArrayOf
        veterinariaArray.add(veterinaria3)

        val veterinaria4 = Veterinaria()
        veterinaria4.id = 3
        veterinaria4.titulo = "Mis patitas"
        veterinaria4.descripcion = "Veterinaria"
        veterinaria4.img = "veterinaria_cuatro"
        veterinaria4.unbigeo = "1030"
        veterinaria4.distrito = "Magdalena"
        veterinaria4.favorito = false
        veterinaria4.horarios.fechas = fechasDisponibleArrayOf
        veterinaria4.horarios.tiempo = tiempoDisponibleArrayOf
        veterinariaArray.add(veterinaria4)

        val veterinaria5 = Veterinaria()
        veterinaria5.id = 4
        veterinaria5.titulo = "Veterinaria Camino al cielo"
        veterinaria5.descripcion = "Veterinaria"
        veterinaria5.img = "veterinaria_cuatro"
        veterinaria5.unbigeo = "1030"
        veterinaria5.distrito = "Magdalena"
        veterinaria5.favorito = false
        veterinaria5.horarios.fechas = fechasDisponibleArrayOf
        veterinaria5.horarios.tiempo = tiempoDisponibleArrayOf
        veterinariaArray.add(veterinaria5)


    }

    fun startRCVHome(){
        val mLayoutManager = GridLayoutManager(this,2)
        rcvVeterinarias?.setLayoutManager(mLayoutManager)
        veterinariaAdapter = VeterinariaAdapter(veterinariaArrayFilter){ veterinaria ->

            //Log.d("Jose",veterinaria.distrito)
            val intent = Intent(this, JourneyTipoOneRegisterActivity::class.java)
            intent.putExtra("OBJECT_VETERINARIA",veterinaria)
            startActivity(intent)

        }
        rcvVeterinarias?.setAdapter(veterinariaAdapter)
        rcvVeterinarias?.setItemAnimator(DefaultItemAnimator())
    }
}