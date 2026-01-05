# Sistema de Gestión de Alumnos, Pagos, Membresías y Clases de Maestros

Aplicación de escritorio desarrollada en Java utilizando Java Swing para la interfaz gráfica y MySQL para las consultas SQL. El sistema utiliza una base de datos H2 en modo embebido, lo que permite ejecutar el programa sin necesidad de instalar un servidor externo.

Este proyecto fue construido en el entorno Visual Studio Code y está diseñado para gestionar la información de alumnos, maestros, membresías, pagos y horarios de clase de manera eficiente.

## Funciones

Registro, edición y eliminación de:
* Alumnos
* Pagos
* Membresías
* Horarios

## Caracteristicas
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

## Capturas
<img width="680" height="390" alt="Menu principal" src="https://github.com/user-attachments/assets/17daf0e1-c2f4-4619-a20f-3f4d907dae6f" />

<img width="680" height="390" alt="Registro de alumnos" src="https://github.com/user-attachments/assets/bbfa5264-e085-4549-b4ae-6b69e46d8d34" />

<img width="680" height="390" alt="Registro realizado exitosamente" src="https://github.com/user-attachments/assets/a774483c-8c16-46ec-a667-6847f5202e5e" />

<img width="680" height="390" alt="Despliege de los registros realizados" src="https://github.com/user-attachments/assets/256daff4-c987-43fb-bdc6-00f562f26ed2" />

<img width="680" height="390" alt="Eliminacion de un registro" src="https://github.com/user-attachments/assets/a0bb4b7c-01b8-47e2-8784-375bb01eb460" />

<img width="680" height="390" alt="Menu de configuracion" src="https://github.com/user-attachments/assets/d8c72efa-6828-4f23-803b-ef28f47af15c" />

## Pasos para ejecutar el proyecto
1. Clonar el repositorio
2. Abrir en Visual Studio Code
3. Comprobar tener instalado:
   * Java 24 o superior
   * "Extension pack for Java" en Visual Studio Code
4. Correr el archivo: src/main/java/com/aquasport/Main.java
5. Ingresar el usuario: _user_ y la contraseña: _1234_
