package com.mastermartini.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mastermartini.app.model.Categoria
import com.mastermartini.app.model.Perfil
import com.mastermartini.app.model.TamanoNegocio
import com.mastermartini.app.model.TipoNegocio
import com.mastermartini.app.util.Seguridad
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "master_martini_prefs")

/**
 * Persistencia local con DataStore Preferences: perfil (datos ficticios),
 * sesión, favoritos, intereses, foto y preferencia de texto grande.
 */
class UserRepository(private val context: Context) {

    private object Claves {
        val NOMBRE = stringPreferencesKey("nombre")
        val CORREO = stringPreferencesKey("correo")
        val TELEFONO = stringPreferencesKey("telefono")
        val RUT = stringPreferencesKey("rut_empresa")
        val TIPO_NEGOCIO = stringPreferencesKey("tipo_negocio")
        val TAMANO = stringPreferencesKey("tamano_negocio")
        val CLAVE_HASH = stringPreferencesKey("clave_hash")
        val INTERESES = stringSetPreferencesKey("intereses")
        val FOTO = stringPreferencesKey("foto_uri")
        val FAVORITOS = stringSetPreferencesKey("favoritos")
        val SESION = booleanPreferencesKey("sesion_activa")
        val TEXTO_GRANDE = booleanPreferencesKey("texto_grande")
    }

    val perfil: Flow<Perfil?> = context.dataStore.data.map { p ->
        val nombre = p[Claves.NOMBRE] ?: return@map null
        Perfil(
            nombre = nombre,
            correo = p[Claves.CORREO].orEmpty(),
            telefono = p[Claves.TELEFONO].orEmpty(),
            rutEmpresa = p[Claves.RUT].orEmpty(),
            tipoNegocio = TipoNegocio.entries.firstOrNull { it.name == p[Claves.TIPO_NEGOCIO] }
                ?: TipoNegocio.OTRO,
            tamano = TamanoNegocio.entries.firstOrNull { it.name == p[Claves.TAMANO] }
                ?: TamanoNegocio.MICRO,
            intereses = p[Claves.INTERESES].orEmpty()
                .mapNotNull { n -> Categoria.entries.firstOrNull { it.name == n } }
                .toSet(),
            fotoUri = p[Claves.FOTO]
        )
    }

    val sesionActiva: Flow<Boolean> = context.dataStore.data.map { it[Claves.SESION] ?: false }

    val favoritos: Flow<Set<Int>> = context.dataStore.data.map { p ->
        p[Claves.FAVORITOS].orEmpty().mapNotNull { it.toIntOrNull() }.toSet()
    }

    val textoGrande: Flow<Boolean> = context.dataStore.data.map { it[Claves.TEXTO_GRANDE] ?: false }

    suspend fun existeCorreo(correo: String): Boolean {
        val p = context.dataStore.data.first()
        return p[Claves.CORREO]?.equals(correo.trim(), ignoreCase = true) == true
    }

    suspend fun registrar(perfil: Perfil, clave: String) {
        context.dataStore.edit {
            it[Claves.NOMBRE] = perfil.nombre.trim()
            it[Claves.CORREO] = perfil.correo.trim()
            it[Claves.TELEFONO] = perfil.telefono.trim()
            it[Claves.RUT] = perfil.rutEmpresa.trim()
            it[Claves.TIPO_NEGOCIO] = perfil.tipoNegocio.name
            it[Claves.TAMANO] = perfil.tamano.name
            it[Claves.CLAVE_HASH] = Seguridad.hash(clave)
            it[Claves.INTERESES] = perfil.intereses.map { c -> c.name }.toSet()
            it.remove(Claves.FOTO)
            it[Claves.SESION] = true
        }
    }

    suspend fun iniciarSesion(correo: String, clave: String): Boolean {
        val p = context.dataStore.data.first()
        val correoOk = p[Claves.CORREO]?.equals(correo.trim(), ignoreCase = true) == true
        val claveOk = p[Claves.CLAVE_HASH] == Seguridad.hash(clave)
        if (correoOk && claveOk) {
            context.dataStore.edit { it[Claves.SESION] = true }
            return true
        }
        return false
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { it[Claves.SESION] = false }
    }

    suspend fun guardarIntereses(intereses: Set<Categoria>) {
        context.dataStore.edit { it[Claves.INTERESES] = intereses.map { c -> c.name }.toSet() }
    }

    suspend fun guardarFoto(uri: String) {
        context.dataStore.edit { it[Claves.FOTO] = uri }
    }

    suspend fun toggleFavorito(id: Int) {
        context.dataStore.edit {
            val actual = it[Claves.FAVORITOS].orEmpty()
            val clave = id.toString()
            it[Claves.FAVORITOS] = if (clave in actual) actual - clave else actual + clave
        }
    }

    suspend fun setTextoGrande(valor: Boolean) {
        context.dataStore.edit { it[Claves.TEXTO_GRANDE] = valor }
    }
}
