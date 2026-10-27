package com.mastermartini.app.util

import java.security.MessageDigest

/** Utilidad para no guardar contraseñas en texto plano (uso académico). */
object Seguridad {
    fun hash(texto: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(texto.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
