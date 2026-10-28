# Master Martini Academia - MVP movil (DSY1105 · Evaluacion Parcial 2)

## Resumen del Proyecto

Master Martini Academia es una solucion movil disenada como prototipo academico para centralizar la entrega de contenido tecnico, recetas de autor y capacitaciones digitales dirigidas a clientes y prospectos de Master Martini Chile SPA (comercializadora de insumos para alimentos).

La aplicacion resuelve la problematica actual de la empresa, la cual difunde sus contenidos audiovisuales de forma informal y desestructurada a traves de canales como WhatsApp y YouTube. Este MVP centraliza dicha oferta formativa mediante una arquitectura nativa Android (Kotlin, Jetpack Compose, Material 3 y MVVM) enfocada en tres pilares principales:

1. **Centralizacion de contenidos:** Organizacion estructurada de recetas, videos tecnicos y cursos formativos.
2. **Accesibilidad e inclusion:** Diseno pensado en emprendedores mayores de 50 anos o con dificultades visuales, ofreciendo opciones de adaptacion como modo de Texto Grande y layouts adaptables segun el dispositivo (Window Size Classes).
3. **Segmentacion de usuarios:** Sistema de recomendaciones personalizadas basado en una encuesta inicial de intereses editable desde el perfil del usuario.

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

- **Splash:** Animacion y carga inicial con CircularProgressIndicator.
- **Login y Registro:** Validacion por campo, iconos, mensajes de error y estado de carga.
- **Encuesta de intereses:** Segmentacion editable desde el perfil.
- **Catalogo:** Contenidos con busqueda, filtros (receta / video / curso) y recomendaciones segun intereses.
- **Detalle:** Contenido, favoritos y opcion de compartir mediante Intent.
- **Favoritos:** Persistencia local de contenidos seleccionados.
- **Perfil:** Foto desde galeria o camara, datos del usuario, intereses, opcion de Texto grande (accesibilidad) y cierre de sesion.
- **Diseno adaptable:** Implementacion con Window Size Classes:
  * Barra inferior + 1 columna (compacta).
  * NavigationRail + 2 columnas (mediana).
  * 3 columnas (expandida).
- **Animaciones:** Transiciones entre pantallas, AnimatedVisibility, AnimatedContent, animateColorAsState, spring en favoritos, Crossfade en la foto y animateContentSize.

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
```

---

## Como ejecutar

1. Abrir **Android Studio** (Koala o superior, JDK 17) -> *Open* -> seleccionar la carpeta del proyecto.
2. Esperar la sincronizacion de Gradle (requiere conexion a internet la primera vez).
3. Elegir un emulador (API 26+) o un telefono fisico con depuracion USB habilitada.
4. Ejecutar **app**.
5. **Primer uso:** Crear cuenta con datos ficticios (ej. RUT `12.345.678-5`, telefono `+56 9 1234 5678`, contrasena con 8+ caracteres, letras y numeros).
6. Probar la camara en un dispositivo fisico (en emulador se utilizara la camara virtual).

Para ejecutar las pruebas unitarias de validacion: `./gradlew test` (o clic derecho en `ValidadoresTest`).

---

## Notas del MVP

- Existe una sola cuenta local por dispositivo (persistida con DataStore); la contrasena se almacena utilizando un hash SHA-256.
- Los colores y el logo son provisionales a la espera de la identidad corporativa autorizada.
- Los videos tecnicos no estan incluidos (material pendiente de autorizacion); el catalogo los despliega como contenido simulado.
