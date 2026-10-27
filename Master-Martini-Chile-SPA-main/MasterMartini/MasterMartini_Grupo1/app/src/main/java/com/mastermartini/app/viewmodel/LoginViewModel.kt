package com.mastermartini.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.app.data.UserRepository
import com.mastermartini.app.util.Validadores
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val correo: String = "",
    val clave: String = "",
    val errorCorreo: String? = null,
    val errorClave: String? = null,
    val errorGeneral: String? = null,
    val intentado: Boolean = false,
    val cargando: Boolean = false,
    val exito: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository(application)

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    fun onCorreoChange(valor: String) {
        _estado.update {
            val nuevo = it.copy(correo = valor, errorGeneral = null)
            if (nuevo.intentado) nuevo.copy(errorCorreo = Validadores.correo(valor)) else nuevo
        }
    }

    fun onClaveChange(valor: String) {
        _estado.update {
            val nuevo = it.copy(clave = valor, errorGeneral = null)
            if (nuevo.intentado) nuevo.copy(errorClave = Validadores.claveLogin(valor)) else nuevo
        }
    }

    fun iniciarSesion() {
        val s = _estado.value
        if (s.cargando) return
        val errorCorreo = Validadores.correo(s.correo)
        val errorClave = Validadores.claveLogin(s.clave)
        _estado.update {
            it.copy(intentado = true, errorCorreo = errorCorreo, errorClave = errorClave, errorGeneral = null)
        }
        if (errorCorreo != null || errorClave != null) return

        viewModelScope.launch {
            _estado.update { it.copy(cargando = true) }
            delay(700) // simula la consulta de credenciales
            val ok = repo.iniciarSesion(s.correo, s.clave)
            _estado.update {
                if (ok) it.copy(cargando = false, exito = true)
                else it.copy(
                    cargando = false,
                    errorGeneral = "Correo o contraseña incorrectos. Revisa tus datos o crea una cuenta."
                )
            }
        }
    }
}
