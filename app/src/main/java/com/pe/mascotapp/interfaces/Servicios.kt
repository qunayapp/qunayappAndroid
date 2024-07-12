package com.pe.mascotapp.interfaces

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.pe.mascotapp.modelos.SesionUsuario
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface Servicios {

    /* GET ALL */

    @GET("login")
    fun getLoginUser(@Query("correo") correo:String, @Query("encriptado") encriptado:String): Call<SesionUsuario>
}