


/src
 ├── /modelo
 │     ├── IdObligatorioException/
 │     ├── Pista/
 │     ├── Reserva/
 │     └── Socio/
 │
 ├── /servicio
 │     └── ClubDeportivo/
 │
 └── /vista
       ├── views
       │     ├── BajaSocioView/
       │     ├── CambiarDisponibilidadView/
       │     ├── CancelarReservaView/
       │     ├── DashboardView/
       │     ├── PistaFormView/
       │     ├── ReservaFormView/
       │     └── SocioFormView/
       │
       └── MainApp/







Características principales
Gestión de Socios

Alta de nuevos socios.

Baja de socios existentes (siempre que no tengan reservas activas).

Campos opcionales: teléfono y email → se guardan como NULL si no se completan.

Validación de ID único.

Gestión de Pistas

Alta de nuevas pistas.

Tipos válidos: Tenis, Fútbol Sala, Pádel.

Modificación del estado de disponibilidad (Disponible / No Disponible).

Validación de ID único.

Gestión de Reservas

Creación de reservas indicando:

Socio

Pista

Fecha (DatePicker)

Hora (HH:mm)

Duración (minutos)

Cancelación de reservas.

Reglas:

Pista no puede tener dos reservas en la misma fecha y hora.

No se permiten reservas en pistas no disponibles.

ID de reserva único.

Otras funciones

Pantalla “Ver”: muestra un resumen de los cambios realizados.

Menú “Archivo → Salir”.

No existe botón “Guardar”, ya que los cambios se almacenan automáticamente en BD.

Tecnologías utilizadas

Java 17/21

JavaFX

JDBC

DBeaver (gestión de base de datos)

MySQL / PostgreSQL / SQLite (según la versión del proyecto)

Maven / Gradle

Git & GitHub

Estructura de la Base de Datos
Tabla Socios

id_socio (PK, único)

nombre

dni

telefono (NULL permitido)

email (NULL permitido)

Tabla Pistas

id_pista (PK, único)

tipo (Tenis / Fútbol Sala / Pádel)

disponible (boolean)

Tabla Reservas

id_reserva (PK, único)

id_socio (FK → Socios)

id_pista (FK → Pistas)

fecha

hora

duracion

Relaciones

Un socio puede tener múltiples reservas.

Una pista puede tener múltiples reservas.

No puede existir una reserva:

En una pista no disponible.

Que coincida en pista + fecha + hora con otra.
