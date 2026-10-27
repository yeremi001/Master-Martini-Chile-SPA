package com.mastermartini.app.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/** Imagen circular de perfil; muestra un ícono por defecto si no hay foto. */
@Composable
fun ImagenInteligente(
    uri: String?,
    modifier: Modifier = Modifier,
    tamano: Dp = 140.dp
) {
    Box(
        modifier = modifier
            .size(tamano)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = uri, animationSpec = tween(500), label = "foto_perfil") { actual ->
            if (actual == null) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Sin foto de perfil",
                    modifier = Modifier.size(tamano * 0.6f),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else {
                AsyncImage(
                    model = actual,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
