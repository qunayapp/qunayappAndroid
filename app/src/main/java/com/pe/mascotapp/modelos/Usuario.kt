package com.pe.mascotapp.modelos

import java.io.Serializable

class Usuario(
    var name: String = "",
    var email: String = "",
    var numPhone: String = "",
    var birthdate: String = "",
    var pass: String = "",
    var terms: Boolean = false
):Serializable {

    fun copy(
        name: String = this.name,
        email: String = this.email,
        numPhone: String = this.numPhone,
        birthdate: String = this.birthdate,
        pass: String = this.pass,
        terms: Boolean = this.terms
    ): Usuario {
        return Usuario(name, email, numPhone, birthdate, pass, terms)
    }

}