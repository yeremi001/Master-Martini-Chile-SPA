package com.mastermartini.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.app.data.ContenidoRepository
import com.mastermartini.app.model.Contenido
import com.mastermartini.app.model.TipoContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ContenidoViewModel : ViewModel() {
    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _tipo = MutableStateFlow<TipoContenido?>(null)
    val tipo: StateFlow<TipoContenido?> = _tipo.asStateFlow()

    val resultados: StateFlow<List<Contenido>> =
        combine(_busqueda, _tipo) { texto, tipo -> ContenidoRepository.buscar(texto, tipo) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, ContenidoRepository.todos)

    fun onBusquedaChange(valor: String) {
        _busqueda.value = valor
    }

    fun onTipoChange(valor: TipoContenido?) {
        _tipo.value = valor
    }
}
