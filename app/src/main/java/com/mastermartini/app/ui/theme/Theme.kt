package com.mastermartini.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

// Paleta provisional (alto contraste). Reemplazar por los colores corporativos autorizados.
private val EsquemaClaro = lightColorScheme(
    primary = Color(0xFF8C1C2E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDAD9),
    onPrimaryContainer = Color(0xFF410008),
    secondary = Color(0xFF7A5900),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDF9E),
    onSecondaryContainer = Color(0xFF261A00),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF201A1A),
    surfaceVariant = Color(0xFFF5DDDC),
    onSurfaceVariant = Color(0xFF534342)
)

/**
 * Tema de la app. [textoGrande] amplía la tipografía un 30 % para usuarios con
 * dificultades visuales (requisito del caso Master Martini).
 */
@Composable
fun MasterMartiniTheme(textoGrande: Boolean = false, content: @Composable () -> Unit) {
    val base = LocalDensity.current
    val densidad = Density(
        density = base.density,
        fontScale = if (textoGrande) base.fontScale * 1.3f else base.fontScale
    )
    CompositionLocalProvider(LocalDensity provides densidad) {
        MaterialTheme(colorScheme = EsquemaClaro, typography = Typography(), content = content)
    }
}
