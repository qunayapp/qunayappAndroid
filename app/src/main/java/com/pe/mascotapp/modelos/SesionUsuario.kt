package com.pe.mascotapp.modelos

import com.google.gson.annotations.SerializedName

data class SesionUsuario(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellidop") val apellidoP: String,
    @SerializedName("apellidom") val apellidoM: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("id_sexo") val idSexo: Int,
    @SerializedName("id_identificacion") val idIdentificacion: Int,
    @SerializedName("fechanacimiento") val fechaNacimiento: String,
    @SerializedName("fecharegistro") val fechaRegistro: String,
    @SerializedName("id_estado") val idEstado: Int,
    @SerializedName("id_perfil_usuario") val idPerfilUsuario: Int,
    @SerializedName("numeroidentificacion") val numeroIdentificacion: Int,
    @SerializedName("id_credenciales") val idCredenciales: Int,
    @SerializedName("encriptado") val encriptado: String,
    @SerializedName("id_usuario_mascota") val idUsuarioMascota: Int,
    @SerializedName("id_mascota") val idMascota: Int,
    @SerializedName("estado") val estado: Int,
    @SerializedName("id_rol_usuario_mascota") val idRolUsuarioMascota: Int,
    @SerializedName("id_permisos_usuario_mascota") val idPermisosUsuarioMascota: Int,
    @SerializedName("apodo") val apodo: String,
    @SerializedName("id_sexo_mascota") val idSexoMascota: Int,
    @SerializedName("id_identificacion_mascota") val idIdentificacionMascota: Int,
    @SerializedName("fechaadopcion") val fechaAdopcion: String,
    @SerializedName("id_especie") val idEspecie: Int,
    @SerializedName("id_rango_pesos") val idRangoPesos: Int
)
