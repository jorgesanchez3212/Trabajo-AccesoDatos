package utils

import com.toxicbakery.bcrypt.Bcrypt

object Contraseña {

    fun encriptarContraseña(contraseña : String) : ByteArray {
        return Bcrypt.hash(contraseña, 12)
    }
}