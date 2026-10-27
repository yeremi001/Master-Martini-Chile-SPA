package com.mastermartini.app.navigation

/** Rutas tipadas de la aplicación. */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Registro : Screen("registro")
    data object Home : Screen("home")
    data object Favoritos : Screen("favoritos")
    data object Perfil : Screen("perfil")
    data object Encuesta : Screen("encuesta")
    data object Detalle : Screen("detalle/{id}") {
        const val ARG = "id"
        fun crearRuta(id: Int) = "detalle/$id"
    }
}
