package com.pe.mascotapp.utils

class ConfigAppSingleton {
    companion object {

        init{
            println("ConfigAppSingleton start");
        }

        fun isProduccion(): Boolean {
            return false
        }

    }

}