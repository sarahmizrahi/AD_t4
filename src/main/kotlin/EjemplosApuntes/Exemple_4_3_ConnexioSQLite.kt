package org.example.EjemplosApuntes

import java.sql.DriverManager


//CONEXION CON SQLite

fun main() {
    //Variable par ala conecion
    val url = "jdbc:sqlite:prueba.sqlite"   //Usa el archivo prueba.sqlite, si no existe lo crea.
                                            //Si existe, se puede poner la ruta

    //Establecer la conexion
    //DriverManager busca en driver
    //getConnection() obtiene la conexion mediante lo datos proporcionados
    val conexion = DriverManager.getConnection(url)
    println("Conexion completada")
    //Cerrar conexion
    conexion.close()
}