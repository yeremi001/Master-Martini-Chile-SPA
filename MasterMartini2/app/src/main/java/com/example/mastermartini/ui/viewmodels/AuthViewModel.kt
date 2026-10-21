package com.example.mastermartini.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class RegisterUiState(
    val rut: String = "",
    val rutError: String? = null,
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val businessType: String = "Pastelería",
    val isValid: Boolean = false
)

class AuthViewModel : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onRutChanged(rut: String) {
        uiState = uiState.copy(rut = rut)
        validateForm()
    }

    fun onNameChanged(name: String) {
        uiState = uiState.copy(name = name)
        validateForm()
    }

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
        validateForm()
    }

    fun onBusinessTypeChanged(type: String) {
        uiState = uiState.copy(businessType = type)
    }

    private fun validateForm() {
        val isRutValid = uiState.rut.length >= 8
        val isNameValid = uiState.name.isNotBlank()
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()

        uiState = uiState.copy(
            rutError = if (isRutValid || uiState.rut.isEmpty()) null else "RUT inválido (mínimo 8 caracteres)",
            nameError = if (isNameValid || uiState.name.isEmpty()) null else "El nombre es obligatorio",
            emailError = if (isEmailValid || uiState.email.isEmpty()) null else "Ingrese un correo válido",
            isValid = isRutValid && isNameValid && isEmailValid
        )
    }
}