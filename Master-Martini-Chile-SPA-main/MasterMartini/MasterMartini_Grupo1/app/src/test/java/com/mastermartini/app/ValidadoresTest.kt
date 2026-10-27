package com.mastermartini.app

import com.mastermartini.app.util.Validadores
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class ValidadoresTest {
    @Test fun rutValidoSinErrores() = assertNull(Validadores.rut("12.345.678-5"))
    @Test fun rutConDigitoIncorrecto() = assertNotNull(Validadores.rut("12.345.678-9"))
    @Test fun correoValido() = assertNull(Validadores.correo("cliente@ejemplo.cl"))
    @Test fun correoInvalido() = assertNotNull(Validadores.correo("cliente@"))
    @Test fun telefonoChilenoValido() = assertNull(Validadores.telefono("+56 9 1234 5678"))
    @Test fun telefonoInvalido() = assertNotNull(Validadores.telefono("12345"))
    @Test fun claveCorta() = assertNotNull(Validadores.clave("abc12"))
    @Test fun claveSinNumeros() = assertNotNull(Validadores.clave("abcdefgh"))
    @Test fun claveValida() = assertNull(Validadores.clave("martini2026"))
    @Test fun confirmacionDistinta() = assertNotNull(Validadores.confirmacion("martini2026", "otra"))
}
