@file:OptIn(ExperimentalMaterial3Api::class)

package com.mastermartini.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastermartini.app.model.TamanoNegocio
import com.mastermartini.app.model.TipoNegocio
import com.mastermartini.app.ui.components.CampoSeleccion
import com.mastermartini.app.ui.components.CampoTexto
import com.mastermartini.app.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    onVolver: () -> Unit,
    onExito: () -> Unit,
    viewModel: RegistroViewModel = viewModel()
) {
    val e by viewModel.estado.collectAsState()
    LaunchedEffect(e.exito) { if (e.exito) onExito() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 560.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Prototipo académico: usa solo datos ficticios (ej. RUT 12.345.678-5).",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                AnimatedVisibility(visible = e.errorGeneral != null) {
                    Text(e.errorGeneral.orEmpty(), color = MaterialTheme.colorScheme.error)
                }

                CampoTexto(
                    valor = e.nombre, onCambio = viewModel::onNombreChange,
                    etiqueta = "Nombre completo", iconoInicial = Icons.Filled.Person,
                    error = e.errores.nombre
                )
                CampoTexto(
                    valor = e.correo, onCambio = viewModel::onCorreoChange,
                    etiqueta = "Correo electrónico", iconoInicial = Icons.Filled.Email,
                    error = e.errores.correo, tipoTeclado = KeyboardType.Email
                )
                CampoTexto(
                    valor = e.telefono, onCambio = viewModel::onTelefonoChange,
                    etiqueta = "Teléfono", iconoInicial = Icons.Filled.Phone,
                    error = e.errores.telefono, tipoTeclado = KeyboardType.Phone,
                    ayuda = "Ej: +56 9 1234 5678"
                )
                CampoTexto(
                    valor = e.rut, onCambio = viewModel::onRutChange,
                    etiqueta = "RUT de empresa", iconoInicial = Icons.Filled.Badge,
                    error = e.errores.rut, ayuda = "Ej: 12.345.678-5"
                )
                CampoSeleccion(
                    valor = e.tipoNegocio?.etiqueta.orEmpty(),
                    etiqueta = "Tipo de negocio",
                    iconoInicial = Icons.Filled.Store,
                    opciones = TipoNegocio.entries.map { it.etiqueta },
                    onSeleccion = { viewModel.onTipoNegocioChange(TipoNegocio.entries[it]) },
                    error = e.errores.tipoNegocio
                )

                Text("Tamaño del negocio", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Column(Modifier.selectableGroup()) {
                    TamanoNegocio.entries.forEach { tamano ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .selectable(
                                    selected = e.tamano == tamano,
                                    onClick = { viewModel.onTamanoChange(tamano) },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = e.tamano == tamano, onClick = null)
                            Spacer(Modifier.width(12.dp))
                            Text(tamano.etiqueta, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
                MensajeError(e.errores.tamano)

                CampoTexto(
                    valor = e.clave, onCambio = viewModel::onClaveChange,
                    etiqueta = "Contraseña", iconoInicial = Icons.Filled.Lock,
                    error = e.errores.clave, esClave = true,
                    ayuda = "Mínimo 8 caracteres, con letras y números"
                )
                CampoTexto(
                    valor = e.confirmacion, onCambio = viewModel::onConfirmacionChange,
                    etiqueta = "Repetir contraseña", iconoInicial = Icons.Filled.Lock,
                    error = e.errores.confirmacion, esClave = true,
                    imeAction = ImeAction.Done, onAccion = viewModel::registrar
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .toggleable(
                            value = e.aceptaTerminos,
                            onValueChange = viewModel::onTerminosChange,
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = e.aceptaTerminos, onCheckedChange = null)
                    Spacer(Modifier.width(12.dp))
                    Text("Acepto los términos y condiciones", style = MaterialTheme.typography.bodyLarge)
                }
                MensajeError(e.errores.terminos)

                Button(
                    onClick = viewModel::registrar,
                    enabled = !e.cargando,
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    AnimatedContent(targetState = e.cargando, label = "boton_registro") { cargando ->
                        if (cargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Registrarme", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}

/** Mensaje de error visual (ícono + texto) para controles que no son campos de texto. */
@Composable
private fun MensajeError(mensaje: String?) {
    AnimatedVisibility(visible = mensaje != null) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(mensaje.orEmpty(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
