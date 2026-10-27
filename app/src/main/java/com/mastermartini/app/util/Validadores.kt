package com.mastermartini.app.util

import com.mastermartini.app.model.Categoria
import com.mastermartini.app.model.TamanoNegocio
import com.mastermartini.app.model.TipoNegocio

/**
 * Reglas de validación centralizadas y desacopladas de la interfaz.
 * Cada función retorna el mensaje de error o null si el valor es válido.
 */
object Validadores {
    private val REGEX_CORREO = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val REGEX_TELEFONO = Regex("^(\\+?56)?9\\d{8}$")

    fun nombre(valor: String): String? = when {
        valor.isBlank() -> "El nombre es obligatorio"
        valor.trim().length < 3 -> "Debe tener al menos 3 caracteres"
        !valor.all { it.isLetter() || it == ' ' || it == '\'' } -> "Usa solo letras y espacios"
        else -> null
    }

    fun correo(valor: String): String? = when {
        valor.isBlank() -> "El correo es obligatorio"
        !REGEX_CORREO.matches(valor.trim()) -> "Correo inválido (ej: nombre@dominio.cl)"
        else -> null
    }

    fun telefono(valor: String): String? {
        if (valor.isBlank()) return "El teléfono es obligatorio"
        val limpio = valor.replace(" ", "").replace("-", "")
        return if (REGEX_TELEFONO.matches(limpio)) null else "Teléfono inválido (ej: +56 9 1234 5678)"
    }

    /** Valida formato y dígito verificador (módulo 11) de un RUT chileno. */
    fun rut(valor: String): String? {
        if (valor.isBlank()) return "El RUT de empresa es obligatorio"
        val limpio = valor.replace(".", "").replace("-", "").uppercase()
        if (limpio.length !in 8..9) return "Formato inválido (ej: 12.345.678-5)"
        val cuerpo = limpio.dropLast(1)
        val dv = limpio.last()
        if (!cuerpo.all { it.isDigit() }) return "Formato inválido (ej: 12.345.678-5)"
        var suma = 0
        var multiplicador = 2
        for (d in cuerpo.reversed()) {
            suma += d.digitToInt() * multiplicador
            multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
        }
        val resto = 11 - (suma % 11)
        val esperado = when (resto) {
            11 -> '0'
            10 -> 'K'
            else -> resto.digitToChar()
        }
        return if (dv == esperado) null else "RUT inválido (dígito verificador incorrecto)"
    }

    fun tipoNegocio(valor: TipoNegocio?): String? =
        if (valor == null) "Selecciona el tipo de negocio" else null

    fun tamano(valor: TamanoNegocio?): String? =
        if (valor == null) "Selecciona el tamaño del negocio" else null

    fun clave(valor: String): String? = when {
        valor.isBlank() -> "La contraseña es obligatoria"
        valor.length < 8 -> "Debe tener al menos 8 caracteres"
        !valor.any { it.isLetter() } || !valor.any { it.isDigit() } -> "Debe incluir letras y números"
        else -> null
    }

    fun claveLogin(valor: String): String? =
        if (valor.isBlank()) "Ingresa tu contraseña" else null

    fun confirmacion(clave: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Repite tu contraseña"
        clave != confirmacion -> "Las contraseñas no coinciden"
        else -> null
    }

    fun terminos(aceptados: Boolean): String? =
        if (aceptados) null else "Debes aceptar los términos para continuar"

    fun intereses(seleccion: Set<Categoria>): String? =
        if (seleccion.isEmpty()) "Selecciona al menos un interés" else null
}
