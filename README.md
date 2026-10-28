# Master Martini Academia - MVP movil

Prototipo academico de una plataforma movil de recetas, videos tecnicos y cursos para clientes y prospectos de Master Martini Chile SPA (comercializadora de insumos para alimentos). Desarrollado en Kotlin + Jetpack Compose + Material 3 con arquitectura MVVM.

> Todos los datos son ficticios o sinteticos. No se usan datos personales reales ni material interno no autorizado.

---

## Integrantes

| Nombre y apellido | Rol en el equipo |
|---|---|
| Yeremi Vásquez | Lead UI/UX Developer & Accessibility Specialist |
| Benjamín Salas | Backend & Data Architecture Lead |

**Equipo N.º:** 11 · **Asignatura:** DSY1105 Desarrollo de Aplicaciones Móviles · **Docente:** Diego Cares

---

## Contexto del caso

Master Martini difunde hoy sus videos por WhatsApp y YouTube, sin constancia ni segmentacion. La app busca una relacion mas fluida con clientes y prospectos (incluyendo emprendedores mayores de 50 anos y con dificultades visuales), reforzar la marca y mantener informacion de intereses segmentada, con posibilidad de escalar a la plataforma web.

---

## Funcionalidades implementadas

* **Splash:** Animacion y carga inicial con CircularProgressIndicator.
* **Login y Registro:** Validacion por campo, iconos, mensajes de error y estado de carga.
* **Encuesta de intereses:** Segmentacion editable desde el perfil.
* **Catalogo:** Contenidos con busqueda, filtros (receta / video / curso) y recomendaciones segun intereses.
* **Detalle:** Contenido, favoritos y opcion de compartir mediante Intent.
* **Favoritos:** Persistencia local de contenidos seleccionados.
* **Perfil:** Foto desde galeria o camara, datos del usuario, intereses, opcion de Texto grande (accesibilidad) y cierre de sesion.
* **Diseno adaptable:** Implementacion con Window Size Classes:
  * Barra inferior + 1 columna (compacta).
  * NavigationRail + 2 columnas (mediana).
  * 3 columnas (expandida).
* **Animaciones:** Transiciones entre pantallas, AnimatedVisibility, AnimatedContent, animateColorAsState, spring en favoritos, Crossfade en la foto y animateContentSize.

---

## Estructura del proyecto

```text
app/src/main/java/com/mastermartini/app
├── model/        Modelos y enums (Contenido, Perfil, Categoria...)
├── data/         ContenidoRepository (datos simulados) y UserRepository (DataStore)
├── viewmodel/    AppViewModel, LoginViewModel, RegistroViewModel, ContenidoViewModel
├── navigation/   Screen (rutas tipadas) y AppNavigation (NavHost)
├── util/         Validadores (logica de validacion) y Seguridad (hash)
└── ui/
    ├── theme/        Tema Material 3 + modo texto grande
    ├── components/   CampoTexto, ImagenInteligente, ContenidoCard, MainScaffold
    ├── screens/      Splash, Login, Registro, Encuesta, Home, Detalle, Favoritos, Perfil
    └── utils/        WindowSizeUtils

---

## Notas del MVP

-Existe una sola cuenta local por dispositivo (persistida con DataStore); la contrasena se almacena utilizando un hash SHA-256.
-Los colores y el logo son provisionales a la espera de la identidad corporativa autorizada.
-Los videos tecnicos no estan incluidos (material pendiente de autorizacion); el catalogo los despliega como contenido simulado.
