package com.mastermartini.app.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.app.data.UserRepository
import com.mastermartini.app.model.Categoria
import com.mastermartini.app.model.Perfil
import com.mastermartini.app.util.Validadores
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/** ViewModel compartido a nivel de Activity: sesión, perfil, favoritos, foto y accesibilidad. */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserRepository(application)

    /** null = aún cargando desde DataStore. */
    val sesion: StateFlow<Boolean?> = repo.sesionActiva
        .map<Boolean, Boolean?> { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val perfil: StateFlow<Perfil?> = repo.perfil
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val favoritos: StateFlow<Set<Int>> = repo.favoritos
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    val textoGrande: StateFlow<Boolean> = repo.textoGrande
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _mensajes = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val mensajes: SharedFlow<String> = _mensajes.asSharedFlow()

    fun toggleFavorito(id: Int) {
        viewModelScope.launch { repo.toggleFavorito(id) }
    }

    fun setTextoGrande(valor: Boolean) {
        viewModelScope.launch { repo.setTextoGrande(valor) }
    }

    fun validarIntereses(seleccion: Set<Categoria>): String? = Validadores.intereses(seleccion)

    fun guardarIntereses(seleccion: Set<Categoria>) {
        viewModelScope.launch { repo.guardarIntereses(seleccion) }
    }

    fun cerrarSesion() {
        viewModelScope.launch { repo.cerrarSesion() }
    }

    /**
     * Copia la imagen (galería o cámara) al almacenamiento interno de la app
     * para que siga disponible aunque el permiso temporal de la URI expire.
     */
    fun guardarFoto(origen: Uri) {
        viewModelScope.launch {
            val ruta = withContext(Dispatchers.IO) {
                runCatching {
                    val ctx = getApplication<Application>()
                    val destino = File(ctx.filesDir, "perfil_${System.currentTimeMillis()}.jpg")
                    val entrada = ctx.contentResolver.openInputStream(origen)
                        ?: return@runCatching null
                    entrada.use { input -> destino.outputStream().use { input.copyTo(it) } }
                    Uri.fromFile(destino).toString()
                }.getOrNull()
            }
            if (ruta != null) {
                repo.guardarFoto(ruta)
                _mensajes.tryEmit("Foto de perfil actualizada")
            } else {
                _mensajes.tryEmit("No se pudo cargar la imagen. Inténtalo nuevamente")
            }
        }
    }
}
