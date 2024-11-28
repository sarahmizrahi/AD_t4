package org.example.EjemplosApuntes

import java.sql.DriverManager


//CONEXION CON POSTGRE
fun main() {

    //Variables para la conexion
    val url = "jdbc:postgresql://89.36.214.106:5432/geo_ad"
    val usuario = "geo_ad"
    val contraseña = "geo_ad"

    //Establecer la conexion
    //DriverManager busca en driver
    //getConnection() obtiene la conexion mediante lo datos proporcionados
    val conexion = DriverManager.getConnection(url, usuario, contraseña)    //Se podria usar directamente los datos, sin las variables
    println("Conexion a geo_ad completada")
    //Cerrar conexion
    conexion.close()
}