package com.pe.mascotapp.interfaces

import com.pe.mascotapp.modelos.SesionUsuario
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class RetrofitServiceApp {

    // Instancia de Retrofit
    /*fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }*/


    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getLoginUser(correo:String, encriptado:String, callback: (SesionUsuario?) -> Unit) {
        retrofit.create(Servicios::class.java).getLoginUser(correo,encriptado).enqueue(object :
            Callback<SesionUsuario> {
            override fun onResponse(call: Call<SesionUsuario>, response: Response<SesionUsuario>) {
                // Procesar respuesta exitosa
                Utils.dump(response.body().toString())
                callback(response.body())
            }

            override fun onFailure(call: Call<SesionUsuario>, t: Throwable) {
                // Procesar error en la petición
                Utils.dump(t.message.toString())
            }
        })
    }
}