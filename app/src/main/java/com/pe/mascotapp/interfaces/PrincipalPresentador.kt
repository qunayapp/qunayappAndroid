package com.pe.mascotapp.interfaces
import android.content.Context
import android.database.Cursor
import com.pe.mascotapp.interfaces.Modelo
import com.pe.mascotapp.modelos.SQLiteDB

class PrincipalPresentador() {

    class VistaStart(context:Context):Presentador.StartVista{

        //private var vista:Vista.Start= vistaPrincipal
        private var modelo: Modelo.Start = SQLiteDB(context)

        override fun error() {
            TODO("Not yet implemented")
        }

        override fun startDatabe(context: Context):Boolean {
            return modelo.startDatabe(context)
        }

        override fun insertar(query:String):Int {
            return modelo.insertar(query)
        }

        override fun leer(query: String):Cursor {
            return modelo.leer(query)
        }

        override fun delete(query: String) {
            return modelo.delete(query)
        }


    }

    /*class RegistrarDatos(vistaPrincipal:Vista.Start):Presentador.RegistrarDatos{
        override fun obtenerDatosDeDNI() {
            TODO("Not yet implemented")
        }

        override fun registrarDatos() {
            TODO("Not yet implemented")
        }


    }*/


}