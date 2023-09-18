package com.pe.mascotapp.modelos

import java.io.Serializable

class Veterinaria:Serializable {

    var id = 0
    var unbigeo = ""
    var distrito = ""
    var titulo = ""
    var descripcion = ""
    var img = ""
    var favorito = false
    var horarios = HorariosVeterinaria()

}