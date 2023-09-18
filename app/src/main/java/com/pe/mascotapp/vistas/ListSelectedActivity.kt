package com.pe.mascotapp.vistas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityMainBinding
import com.pe.mascotapp.interfaces.PrincipalPresentador
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.Raza
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.adapters.HomeAdapter
import com.pe.mascotapp.vistas.adapters.ListSelectedAdapter
import java.util.*
import java.util.stream.IntStream
import kotlin.collections.ArrayList

class ListSelectedActivity : AppCompatActivity() {

    var rcvList: RecyclerView?= null
    var listSelectedAdapter: ListSelectedAdapter?= null
    var razaList:ArrayList<Raza> = ArrayList()
    var razaListSelected:ArrayList<Raza> = ArrayList()
    //var txtSelected:TextView ?= null
    var lnlDynamically:LinearLayout ?= null
    var btnDone:Button ?= null
    private lateinit var presentador: PrincipalPresentador.VistaStart

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_selected)

        rcvList = findViewById<RecyclerView>(R.id.rcvList)
        //txtSelected = findViewById<TextView>(R.id.txtSelected)
        lnlDynamically = findViewById<LinearLayout>(R.id.lnlDynamically)
        btnDone = findViewById<Button>(R.id.btnDone)

        presentador = PrincipalPresentador.VistaStart(this)
        btnDone!!.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selected", razaListSelected)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        getData()
    }

    @SuppressLint("Range")
    fun getData(){

        val str = "SELECT * FROM razas where idEspecie == 1"
        val data = presentador.leer(str)
        if (data.moveToFirst()){
            do {
                val raza = Raza()
                val id = data.getInt(data.getColumnIndex("id"))
                val idEspecie = data.getInt(data.getColumnIndex("idEspecie"))
                val name = data.getString(data.getColumnIndex("name"))
                val description = data.getString(data.getColumnIndex("description"))

                raza.id = id
                raza.idEspecie = idEspecie
                raza.nombre = name
                raza.descripcion = description
                razaList.add(raza)

            }while (data.moveToNext())
        }

        startRcv()
    }

    fun startRcv(){
        rcvList?.setLayoutManager(LinearLayoutManager(this))
        listSelectedAdapter = ListSelectedAdapter(razaList){ raza, holder ->
            Utils.dump(raza.nombre)
            var found = false
            if (razaListSelected.size > 0){
                for (item in razaListSelected){
                    if (raza.id == item.id){
                        razaListSelected.remove(item)
                        holder.imgCheck.visibility = View.GONE
                        found = true
                        break
                    }
                }
            }
            if(!found){
                holder.imgCheck.visibility = View.VISIBLE
                razaListSelected.add(raza)
            }
            lnlDynamically!!.removeAllViews()
            for (item in razaListSelected){
                val child = LayoutInflater.from(this).inflate(R.layout.raza_selected_holder,null)
                val txtRazaSelected = child.findViewById<TextView>(R.id.txtRazaSelected)
                txtRazaSelected.text = item.nombre
                lnlDynamically!!.addView(child)
            }


        }
        rcvList?.setAdapter(listSelectedAdapter)
        rcvList?.setItemAnimator(DefaultItemAnimator())
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}