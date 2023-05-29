package com.example.itvspringapplication.utils

import com.toxicbakery.bcrypt.Bcrypt

object Cifrado {

    fun encriptarContraseña(contraseña : String) : ByteArray {
        return Bcrypt.hash(contraseña, 12)
    }
}