package com.pe.mascotapp.interfaces

import android.content.Context
import android.database.Cursor

interface Presentador {

    interface StartVista{
        fun error()
        fun startDatabe(context: Context):Boolean
        fun insertar(query:String):Int
        fun leer(query:String):Cursor
        fun delete(query:String)
    }

    interface RegistrarDatos{
        fun obtenerDatosDeDNI()
        fun registrarDatos()
    }

}