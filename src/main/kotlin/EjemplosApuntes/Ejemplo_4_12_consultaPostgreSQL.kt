package org.example.EjemplosApuntes

import java.sql.DriverManager

//Consulta en postgresql, devuelve datos

//OJO, no reutilizar Statement y ResultSet
//Para varias consultas hacer esto:

        /* Primera consulta
        val statement1 = conexion.createStatement()
        val rs1 = statement1.executeQuery("SELECT * FROM tabla1")
        while (rs1.next()) {
            println(rs1.getString("columna"))
        }
        rs1.close()
        statement1.close()

        Segunda consulta
        val statement2 = conexion.createStatement()
        val rs2 = statement2.executeQuery("SELECT * FROM tabla2")
        while (rs2.next()) {
            println(rs2.getString("columna"))
        }
        rs2.close()
        statement2.close()*/

fun main() {
    val url = "jdbc:postgresql://89.36.214.106:5432/geo_ad"
    val usuario = "geo_ad"
    val contraseña = "geo_ad"

    val conexion = DriverManager.getConnection(url, usuario, contraseña)

    val comandos = conexion.createStatement()

    //Ejecucion consulta SELECT
    // y el resultado lo guarda en un objeto ResultSet llamado rs
    val rs = comandos.executeQuery("SELECT * FROM institut")

    //Itera por cada fila del ResultSet
    //rs.next() avanza a la siguiente fila, cuando se acaba devuelve false
    while (rs.next()) {
        //Obtiene el valor de la primera columna, Empieza en 1 NO EN CERO
        println(""+rs.getInt(1)+ "\t")
        println(rs.getString(2))
    }

    comandos.close()
    conexion.close()

}