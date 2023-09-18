package com.pe.mascotapp.interfaces

import android.content.Context
import android.database.Cursor

interface Modelo {

    interface Start{
        fun startDatabe(context: Context):Boolean
        fun insertar(query:String):Int
        fun leer(query:String):Cursor
        fun delete(query:String)

    }

    /*interface Usuario{
    }

    interface RegistrarDatos{
        fun obtenerDatosDeDNI()
        fun registrarDatos()
    }*/
}