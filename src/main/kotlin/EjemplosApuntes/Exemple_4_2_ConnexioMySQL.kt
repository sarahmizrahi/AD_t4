package org.example.EjemplosApuntes

import java.sql.DriverManager


//CONEXION CON MySQL

fun main() {
    //Variables para la conexion
    val url = "jdbc:mysql://89.36.214.106:3306/factura"
    val usuario = "factura"
    val contraseña = "factura"

    //Establecer la conexion
    //DriverManager busca en driver
    //getConnection() obtiene la conexion mediante lo datos proporcionados
    val conexion = DriverManager.getConnection(url, usuario, contraseña)//Se podria usar directamente los datos, sin las variables
    println("Conexion completada")
    //Cerrar conexion
    conexion.close()
}