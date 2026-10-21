package com.example.mastermartini.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mastermartini.ui.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Master Martini Academy",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Registro de Cliente / Emprendedor",
            style = MaterialTheme.typography.titleMedium
        )

        // Campo RUT
        OutlinedTextField(
            value = state.rut,
            onValueChange = { viewModel.onRutChanged(it) },
            label = { Text("RUT Empresa o Personal") },
            isError = state.rutError != null,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = state.rutError != null) {
            Text(text = state.rutError ?: "", color = MaterialTheme.colorScheme.error)
        }

        // Campo Nombre
        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChanged(it) },
            label = { Text("Nombre Completo") },
            isError = state.nameError != null,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = state.nameError != null) {
            Text(text = state.nameError ?: "", color = MaterialTheme.colorScheme.error)
        }

        // Campo Correo
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            label = { Text("Correo Electrónico") },
            isError = state.emailError != null,
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(visible = state.emailError != null) {
            Text(text = state.emailError ?: "", color = MaterialTheme.colorScheme.error)
        }

        // Botón
        Button(
            onClick = onRegisterSuccess,
            enabled = state.isValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Ingresar a la Plataforma")
        }
    }
}