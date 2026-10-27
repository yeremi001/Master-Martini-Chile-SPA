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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastermartini.app.ui.components.CampoTexto
import com.mastermartini.app.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onIrRegistro: () -> Unit,
    onExito: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val e by viewModel.estado.collectAsState()
    LaunchedEffect(e.exito) { if (e.exito) onExito() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Cake,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                "Bienvenido a Master Martini",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Ingresa para ver recetas, videos y cursos para tu negocio.",
                style = MaterialTheme.typography.bodyLarge
            )

            AnimatedVisibility(visible = e.errorGeneral != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.width(8.dp))
                        Text(e.errorGeneral.orEmpty(), color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }

            CampoTexto(
                valor = e.correo,
                onCambio = viewModel::onCorreoChange,
                etiqueta = "Correo electrónico",
                iconoInicial = Icons.Filled.Email,
                error = e.errorCorreo,
                tipoTeclado = KeyboardType.Email
            )
            CampoTexto(
                valor = e.clave,
                onCambio = viewModel::onClaveChange,
                etiqueta = "Contraseña",
                iconoInicial = Icons.Filled.Lock,
                error = e.errorClave,
                esClave = true,
                imeAction = ImeAction.Done,
                onAccion = viewModel::iniciarSesion
            )

            Button(
                onClick = viewModel::iniciarSesion,
                enabled = !e.cargando,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                AnimatedContent(targetState = e.cargando, label = "boton_login") { cargando ->
                    if (cargando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Ingresar", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            TextButton(onClick = onIrRegistro) {
                Text("¿Primera vez? Crear cuenta", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}
