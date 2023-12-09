package com.pe.mascotapp.vistas.fragments.tabsService

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.adapters.ComentariosAdapter
import com.pe.mascotapp.vistas.adapters.HomeServiceAdapter
import java.io.File
import java.io.FileInputStream

class TabTwo : Fragment() {

    var rcvComentarios: RecyclerView?= null
    var ComentariosAdapterType: ComentariosAdapter?= null
    var categoriasArray: ArrayList<Categorias> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_step_two, container,false)
        rcvComentarios = view.findViewById<RecyclerView>(R.id.rcvComentarios)
        obtenerData()

        rcvComentarios?.setLayoutManager(LinearLayoutManager(context))
        ComentariosAdapterType = ComentariosAdapter(categoriasArray) { categorias ->

        }

        rcvComentarios?.setAdapter(ComentariosAdapterType)
        rcvComentarios?.setItemAnimator(DefaultItemAnimator())

        return view
    }

    fun obtenerData(){

        val categorias1 = Categorias()
        categorias1.id = 0
        categorias1.titulo = "Comida y Snacks"
        categorias1.descripcion = "Comida y variados"
        categorias1.img = "snack_comida"
        categoriasArray.add(categorias1)

        val categorias2 = Categorias()
        categorias2.id = 1
        categorias2.titulo = "Paseos y entrenamiento"
        categorias2.descripcion = "Paseos"
        categorias2.img = "paseo_perros"
        categoriasArray.add(categorias2)


        val categorias3 = Categorias()
        categorias3.id = 2
        categorias3.titulo = "Juguetes, ropa y accesorios"
        categorias3.descripcion = "juguetes y variados"
        categorias3.img = "juguetes_perros"
        categoriasArray.add(categorias3)

        val categorias4 = Categorias()
        categorias4.id = 3
        categorias4.titulo = "Veterinaria"
        categorias4.descripcion = "Veterinaria"
        categorias4.img = "veterinaria_img"
        categoriasArray.add(categorias4)

        val categorias5 = Categorias()
        categorias5.id = 4
        categorias5.titulo = "Juguetes, ropa y accesorios"
        categorias5.descripcion = "juguetes y variados"
        categorias5.img = "juguetes_perros"
        categoriasArray.add(categorias5)

        val categorias6 = Categorias()
        categorias6.id = 5
        categorias6.titulo = "Juguetes, ropa y accesorios"
        categorias6.descripcion = "juguetes y variados"
        categorias6.img = "juguetes_perros"
        categoriasArray.add(categorias6)

        val categorias7 = Categorias()
        categorias7.id = 6
        categorias7.titulo = "Juguetes, ropa y accesorios"
        categorias7.descripcion = "juguetes y variados"
        categorias7.img = "juguetes_perros"
        categoriasArray.add(categorias7)

        val categorias8 = Categorias()
        categorias8.id =7
        categorias8.titulo = "Juguetes, ropa y accesorios"
        categorias8.descripcion = "juguetes y variados"
        categorias8.img = "juguetes_perros"
        categoriasArray.add(categorias8)

        val categorias9 = Categorias()
        categorias9.id =8
        categorias9.titulo = "Cerrar sesión"
        categorias9.descripcion = "Cerrar sesión"
        categorias9.img = "ic_baseline_logout_24"
        categoriasArray.add(categorias9)


    }

    companion object {
        fun newInstance() : TabTwo {
            val tabTwo = TabTwo()
            return tabTwo
        }
    }
}