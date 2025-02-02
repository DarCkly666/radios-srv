# API Rest de Gestión de Radios

Esta es una API Rest desarrollada con **Spring Boot** que permite gestionar radios, categorías, países e imágenes. La API incluye autenticación básica (username y password) y manejo de roles para controlar el acceso a los diferentes endpoints.

## Características principales

- **Autenticación básica**: Los usuarios deben proporcionar su nombre de usuario y contraseña en cada solicitud.
- **Gestión de usuarios**: Los usuarios pueden registrarse y autenticarse. Los roles incluyen `USER`, `EDITOR` y `ADMIN`.
- **Gestión de radios**: Permite crear, leer, actualizar y eliminar radios.
- **Gestión de categorías y países**: Permite gestionar categorías y países asociados a las radios.
- **Subida de imágenes**: Permite subir imágenes y generar miniaturas automáticamente.
- **Control de acceso basado en roles**: Los endpoints están protegidos según el rol del usuario.

---

## Requisitos previos

Antes de ejecutar la API, asegúrate de tener instalado lo siguiente:

- **Java 17 o superior**.
- **Maven** (para gestionar dependencias y compilar el proyecto).
- **PostgreSQL** (como base de datos).
- **Postman** o cualquier cliente HTTP para probar los endpoints.

---

## Configuración

### 1. Configuración de la base de datos

La API usa **PostgreSQL** como base de datos. Asegúrate de tener una base de datos creada y actualiza el archivo `application.properties` con las credenciales de tu base de datos.

#### `application.properties`:
```properties```
```spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_de_tu_base_de_datos```
```spring.datasource.username=tu_usuario```
```spring.datasource.password=tu_contraseña```
```spring.jpa.hibernate.ddl-auto=update```
```spring.jpa.show-sql=true```

### 2. Configuración de usuarios

Los usuarios se almacenan en la base de datos. Puedes registrar nuevos usuarios usando el endpoint de registro.

---

## Endpoints

### Autenticación y usuarios
#### Registrar un nuevo usuario:

```Método: POST```

```Ruta: /api/v1/user/register```

Cuerpo:
```json
{
  "username": "nombre_de_usuario",
  "password": "contraseña",
  "role": "USER" | "EDITOR" | "ADMIN"
}
```


### Radios
#### Obtener todas las radios:

- **Roles permitidos: USER, EDITOR, ADMIN**

```Método: GET```

```Ruta: /api/v1/radio```


#### Crear una nueva radio:

- **Roles permitidos: EDITOR, ADMIN**
  
```Método: POST```

```Ruta: /api/v1/radio```

Cuerpo:

```json
{
  "name": "Nombre de la radio",
  "description": "Descripción de la radio",
  "url": "https://radio.com",
  "imageLarge": "ruta_de_la_imagen_grande",
  "imageThumbnail": "ruta_de_la_miniatura",
  "categoryId": 1,
  "countryId": 1
}
```

#### Actualizar una radio:

- **Roles permitidos: EDITOR, ADMIN**
  
```Método: PUT```

```Ruta: /api/v1/radio/{id}```

#### Eliminar una radio:

- **Roles permitidos: EDITOR, ADMIN**
  
```Método: DELETE```

```Ruta: /api/v1/radio/{id}```


### Categorías

#### Obtener todas las categorías:

- **Roles permitidos: USER, EDITOR, ADMIN**

```Método: GET```

```Ruta: /api/v1/category```


#### Crear una nueva categoría:

- **Roles permitidos: EDITOR, ADMIN**

```Método: POST```

```Ruta: /api/v1/category```

### Países

#### Obtener todos los países:

- **Roles permitidos: USER, EDITOR, ADMIN**

```Método: GET```

```Ruta: /api/v1/country```

#### Crear un nuevo país:

- **Roles permitidos: EDITOR, ADMIN**

```Método: POST```

```Ruta: /api/v1/country```

### Imágenes

#### Subir una imagen:

- **Roles permitidos: EDITOR, ADMIN**

```Método: POST```

```Ruta: /api/v1/image```


Formato: multipart/form-data

Campo: image (archivo de imagen)


#### Eliminar una imagen:

- **Roles permitidos: EDITOR, ADMIN**

```Método: DELETE```

```Ruta: /api/v1/image/{id}```


### Roles y permisos
- **USER:** Puede leer radios, categorías, países e imágenes.
- **EDITOR:** Puede crear, actualizar y eliminar radios, categorías, países e imágenes.
- **ADMIN:** Tiene acceso completo a todos los endpoints, incluida la gestión de usuarios.