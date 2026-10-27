package com.mastermartini.app.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastermartini.app.ui.screens.DetalleScreen
import com.mastermartini.app.ui.screens.EncuestaScreen
import com.mastermartini.app.ui.screens.FavoritosScreen
import com.mastermartini.app.ui.screens.HomeScreen
import com.mastermartini.app.ui.screens.LoginScreen
import com.mastermartini.app.ui.screens.PerfilScreen
import com.mastermartini.app.ui.screens.RegistroScreen
import com.mastermartini.app.ui.screens.SplashScreen
import com.mastermartini.app.viewmodel.AppViewModel
import com.mastermartini.app.viewmodel.ContenidoViewModel

@Composable
fun AppNavigation(appViewModel: AppViewModel, anchoVentana: WindowWidthSizeClass) {
    val nav = rememberNavController()
    val contenidoViewModel: ContenidoViewModel = viewModel()

    NavHost(
        navController = nav,
        startDestination = Screen.Splash.route,
        enterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 8 } },
        exitTransition = { fadeOut(tween(200)) },
        popEnterTransition = { fadeIn(tween(300)) },
        popExitTransition = { fadeOut(tween(200)) + slideOutHorizontally(tween(300)) { it / 8 } }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(appViewModel) { haySesion ->
                val destino = if (haySesion) Screen.Home.route else Screen.Login.route
                nav.navigate(destino) { popUpTo(Screen.Splash.route) { inclusive = true } }
            }
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onIrRegistro = { nav.navigate(Screen.Registro.route) },
                onExito = {
                    nav.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                }
            )
        }
        composable(Screen.Registro.route) {
            RegistroScreen(
                onVolver = { nav.popBackStack() },
                onExito = {
                    nav.navigate(Screen.Home.route) { popUpTo(Screen.Login.route) { inclusive = true } }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(appViewModel, contenidoViewModel, anchoVentana, nav)
        }
        composable(Screen.Favoritos.route) {
            FavoritosScreen(appViewModel, anchoVentana, nav)
        }
        composable(Screen.Perfil.route) {
            PerfilScreen(
                appViewModel = appViewModel,
                anchoVentana = anchoVentana,
                navController = nav,
                onCerrarSesion = {
                    nav.navigate(Screen.Login.route) { popUpTo(nav.graph.id) { inclusive = true } }
                }
            )
        }
        composable(Screen.Encuesta.route) {
            EncuestaScreen(appViewModel) { nav.popBackStack() }
        }
        composable(
            route = Screen.Detalle.route,
            arguments = listOf(navArgument(Screen.Detalle.ARG) { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt(Screen.Detalle.ARG) ?: -1
            DetalleScreen(id, appViewModel) { nav.popBackStack() }
        }
    }
}
