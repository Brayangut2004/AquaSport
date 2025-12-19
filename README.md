# Sistema de Gestión de Alumnos, Pagos, Membresías y Clases de Maestros

Aplicación de escritorio desarrollada en Java utilizando Java Swing para la interfaz gráfica y MySQL para las consultas SQL. El sistema utiliza una base de datos H2 en modo embebido, lo que permite ejecutar el programa sin necesidad de instalar un servidor externo.

Este proyecto fue construido en el entorno Visual Studio Code y está diseñado para gestionar la información de alumnos, maestros, membresías, pagos y horarios de clase de manera eficiente.

## Características principales

Registro, edición y eliminación de:
* Alumnos
* Pagos
* Membresías
* Horarios

Ademas de:
* Interfaz gráfica creada con Java Swing
* Consultas SQL usando MySQL como lenguaje de consulta
* Base de datos embebida H2 (archivo local, sin servidores externos)
* Validación de datos en formularios
* Gestión de cupos por clase
* Cálculo automático de inscritos y cupos restantes

### Tecnologías utilizadas
Backend:
* Java 24+
* JDBC (Java Database Connectivity)
* H2 Database (modo embebido)

Frontend:
* Java Swing

Base de datos:
* MySQL

Entorno de desarrollo:
* Visual Studio Code

## Pasos para ejecutar el proyecto
1. Clonar el repositorio
2. Abrir en Visual Studio Code
3. Comprobar tener instalado:
   * Java 24 o superior
   * "Extension pack for Java" en Visual Studio Code
4. Ejecutar la aplicacion
   * Correr el archivo: com.aquasport.main