package com.example.mastermartini.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    recipeTitle: String = "Templado de Chocolate Aribao"
) {
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher Cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedBitmap = bitmap
        selectedImageUri = null
    }

    // Launcher Galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        capturedBitmap = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = recipeTitle,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Sube o captura la foto de tu resultado técnico para revisión:",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { cameraLauncher.launch() }) {
                Text("📷 Tomar Foto")
            }

            OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
                Text("🖼️ Galería")
            }
        }

        // Vista previa de la imagen
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    capturedBitmap != null -> {
                        Image(
                            bitmap = capturedBitmap!!.asImageBitmap(),
                            contentDescription = "Foto capturada",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    selectedImageUri != null -> {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Foto de galería",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        Text("Sin evidencia adjunta todavía.")
                    }
                }
            }
        }
    }
}