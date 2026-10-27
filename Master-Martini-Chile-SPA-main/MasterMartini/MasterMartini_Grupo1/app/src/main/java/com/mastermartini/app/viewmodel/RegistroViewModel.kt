package com.mastermartini.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.app.data.UserRepository
import com.mastermartini.app.model.Perfil
import com.mastermartini.app.model.TamanoNegocio
import com.mastermartini.app.model.TipoNegocio
import com.mastermartini.app.util.Validadores
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistroErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val rut: String? = null,
    val tipoNegocio: String? = null,
    val tamano: String? = null,
    val clave: String? = null,
    val confirmacion: String? = null,
    val terminos: String? = null
) {
    fun hayErrores(): Boolean = listOfNotNull(
        nombre, correo, telefono, rut, tipoNegocio, tamano, clave, confirmacion, terminos
    ).isNotEmpty()
}

data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val rut: String = "",
    val tipoNegocio: TipoNegocio? = null,
    val tamano: TamanoNegocio? = null,
    val clave: String = "",
    val confirmacion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: RegistroErrores = RegistroErrores(),
    val intentado: Boolean = false,
    val cargando: Boolean = false,
    val errorGeneral: String? = null,
    val exito: Boolean = false
)

class RegistroViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository(application)

    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado.asStateFlow()

    private fun calcularErrores(s: RegistroUiState) = RegistroErrores(
        nombre = Validadores.nombre(s.nombre),
        correo = Validadores.correo(s.correo),
        telefono = Validadores.telefono(s.telefono),
        rut = Validadores.rut(s.rut),
        tipoNegocio = Validadores.tipoNegocio(s.tipoNegocio),
        tamano = Validadores.tamano(s.tamano),
        clave = Validadores.clave(s.clave),
        confirmacion = Validadores.confirmacion(s.clave, s.confirmacion),
        terminos = Validadores.terminos(s.aceptaTerminos)
    )

    /** Aplica el cambio y, si ya se intentó enviar, revalida en tiempo real. */
    private fun actualizar(cambio: (RegistroUiState) -> RegistroUiState) {
        _estado.update {
            val nuevo = cambio(it).copy(errorGeneral = null)
            if (nuevo.intentado) nuevo.copy(errores = calcularErrores(nuevo)) else nuevo
        }
    }

    fun onNombreChange(v: String) = actualizar { it.copy(nombre = v) }
    fun onCorreoChange(v: String) = actualizar { it.copy(correo = v) }
    fun onTelefonoChange(v: String) = actualizar { it.copy(telefono = v) }
    fun onRutChange(v: String) = actualizar { it.copy(rut = v) }
    fun onTipoNegocioChange(v: TipoNegocio) = actualizar { it.copy(tipoNegocio = v) }
    fun onTamanoChange(v: TamanoNegocio) = actualizar { it.copy(tamano = v) }
    fun onClaveChange(v: String) = actualizar { it.copy(clave = v) }
    fun onConfirmacionChange(v: String) = actualizar { it.copy(confirmacion = v) }
    fun onTerminosChange(v: Boolean) = actualizar { it.copy(aceptaTerminos = v) }

    fun registrar() {
        val s = _estado.value
        if (s.cargando) return
        val errores = calcularErrores(s)
        _estado.update { it.copy(intentado = true, errores = errores, errorGeneral = null) }
        if (errores.hayErrores()) return

        viewModelScope.launch {
            _estado.update { it.copy(cargando = true) }
            delay(800) // simula el envío al servidor
            if (repo.existeCorreo(s.correo)) {
                _estado.update {
                    it.copy(
                        cargando = false,
                        errores = it.errores.copy(correo = "Ya existe una cuenta con este correo")
                    )
                }
                return@launch
            }
            repo.registrar(
                Perfil(
                    nombre = s.nombre,
                    correo = s.correo,
                    telefono = s.telefono,
                    rutEmpresa = s.rut,
                    tipoNegocio = s.tipoNegocio ?: TipoNegocio.OTRO,
                    tamano = s.tamano ?: TamanoNegocio.MICRO
                ),
                s.clave
            )
            _estado.update { it.copy(cargando = false, exito = true) }
        }
    }
}
