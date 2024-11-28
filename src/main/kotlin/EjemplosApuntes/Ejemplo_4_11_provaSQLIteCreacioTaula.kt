package org.example.EjemplosApuntes

import java.sql.DriverManager

//CREAR TABLA EN SQLite

fun main() {
    Class.forName("org.sqlite.JDBC")

    val url = "jdbc:sqlite:prueba.sqlite"

    val conexion = DriverManager.getConnection(url) //Variable para conectar y ejecutar consultas

    val comandos = conexion.createStatement()        //Crea objeto Statement para ejecutar comandos
    comandos.executeUpdate("Create table T4 (c1 text)")   //executeUpdae() se usa para sentencias que no devuelven datos
                            //Crear table T2 con una columna c1 de tipo Text
    comandos.close()  //Cierra la instruccion

    conexion.close()    //Cierra la conexion
}

