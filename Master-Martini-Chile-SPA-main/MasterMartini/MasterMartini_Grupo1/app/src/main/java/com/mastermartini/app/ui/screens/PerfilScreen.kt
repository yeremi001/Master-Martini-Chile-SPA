@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.mastermartini.app.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.mastermartini.app.navigation.Screen
import com.mastermartini.app.ui.components.ImagenInteligente
import com.mastermartini.app.ui.components.MainScaffold
import com.mastermartini.app.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.io.File

/**
 * Perfil del usuario con dos recursos nativos integrados:
 *  1) Galería (GetContent)  2) Cámara (TakePicture + FileProvider + permiso CAMERA).
 */
@Composable
fun PerfilScreen(
    appViewModel: AppViewModel,
    anchoVentana: WindowWidthSizeClass,
    navController: NavController,
    onCerrarSesion: () -> Unit
) {
    val perfil by appViewModel.perfil.collectAsState()
    val textoGrande by appViewModel.textoGrande.collectAsState()
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }
    var uriTemporal by rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) { appViewModel.mensajes.collect { snackbar.showSnackbar(it) } }

    fun avisar(texto: String) {
        scope.launch { snackbar.showSnackbar(texto) }
    }

    val galeria = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) appViewModel.guardarFoto(uri) else avisar("No seleccionaste ninguna imagen")
    }

    val camara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { exito ->
        val uri = uriTemporal
        if (exito && uri != null) appViewModel.guardarFoto(uri) else avisar("Captura cancelada")
    }

    fun lanzarCamara() {
        val carpeta = File(ctx.cacheDir, "images").apply { mkdirs() }
        val archivo = File(carpeta, "captura_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(ctx, "${ctx.packageName}.fileprovider", archivo)
        uriTemporal = uri
        camara.launch(uri)
    }

    val permisoCamara = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) lanzarCamara()
        else avisar("Sin permiso de cámara no podemos tomar la foto. Puedes usar la galería.")
    }

    MainScaffold(navController, "Mi perfil", anchoVentana, snackbar) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .widthIn(max = 640.dp)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ImagenInteligente(uri = perfil?.fotoUri)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { galeria.launch("image/*") }, modifier = Modifier.height(52.dp)) {
                        Icon(Icons.Filled.PhotoLibrary, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Galería")
                    }
                    Button(
                        onClick = {
                            val permiso = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA)
                            if (permiso == PackageManager.PERMISSION_GRANTED) lanzarCamara()
                            else permisoCamara.launch(Manifest.permission.CAMERA)
                        },
                        modifier = Modifier.height(52.dp)
                    ) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Cámara")
                    }
                }

                Text(
                    perfil?.nombre.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FilaDato(Icons.Filled.Email, "Correo", perfil?.correo.orEmpty())
                        FilaDato(Icons.Filled.Phone, "Teléfono", perfil?.telefono.orEmpty())
                        FilaDato(Icons.Filled.Badge, "RUT de empresa", perfil?.rutEmpresa.orEmpty())
                        FilaDato(
                            Icons.Filled.Store, "Negocio",
                            "${perfil?.tipoNegocio?.etiqueta.orEmpty()} · ${perfil?.tamano?.etiqueta.orEmpty()}"
                        )
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Mis intereses",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = { navController.navigate(Screen.Encuesta.route) }) {
                                Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Editar")
                            }
                        }
                        val intereses = perfil?.intereses.orEmpty()
                        if (intereses.isEmpty()) {
                            Text("Aún no has elegido intereses.")
                        } else {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                intereses.forEach { AssistChip(onClick = {}, label = { Text(it.etiqueta) }) }
                            }
                        }
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.TextFields, contentDescription = null)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Texto grande", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text("Aumenta el tamaño de las letras", style = MaterialTheme.typography.bodyMedium)
                        }
                        Switch(checked = textoGrande, onCheckedChange = appViewModel::setTextoGrande)
                    }
                }

                OutlinedButton(
                    onClick = {
                        appViewModel.cerrarSesion()
                        onCerrarSesion()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión")
                }
            }
        }
    }
}

@Composable
private fun FilaDato(icono: ImageVector, etiqueta: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(etiqueta, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(valor, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
