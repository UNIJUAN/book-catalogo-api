# API de Catálogo de Libros

Una aplicación de consola desarrollada en Java con Spring Boot que permite buscar y gestionar libros del Proyecto Gutenberg a través de la API Gutendex.

## Características

- Búsqueda de libros por título
- Listado de libros registrados en base de datos
- Listado de autores
- Filtrado de libros por idioma (es, en, fr, pt)
- Persistencia de datos usando PostgreSQL
- Integración con la API Gutendex

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.4.1
- Spring Data JPA
- PostgreSQL
- Project Lombok
- Jackson para procesamiento JSON

## Prerequisitos

- JDK 21 o superior
- PostgreSQL 12 o superior
- Maven 3.6 o superior

## Configuración

1. Clonar el repositorio:
1.Crear una base de datos PostgreSQL llamada 'book_catalogo'
2.Copiar application.properties.example a application.properties
3.Actualizar las credenciales de base de datos en application.properties

## Ejecución

1.Compilar el proyecto
- mnv clean install

2.Ejecutar la aplicación
- mnv spring-boot:run

## Uso
La aplicación muestra un menú con las siguientes opciones:

1.Buscar libro por título
2.Listar libros registrados
3.Listar autores registrados
4.Listar libros por idioma
0.Salir

## Etructura del Proyecto

src/
├── main/
│   ├── java/
│   │   └── com/example/catalog/bookcatalog/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── controller/
│   └── resources/

## Autor
- UNIJUAN - juan.bogota@uniminuto.edu.co

