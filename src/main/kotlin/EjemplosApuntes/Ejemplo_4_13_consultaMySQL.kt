package org.example.EjemplosApuntes

import java.sql.DriverManager


//Crea consulta SELECT en Mysql
fun main() {
    val url = "jdbc:mysql://89.36.214.106:3306/factura"
    val usuari = "factura"
    val password = "factura"

    val conexion = DriverManager.getConnection(url, usuari, password)

    val comando = conexion.createStatement()
    val rs = comando.executeQuery("SELECT * FROM poble")
    while (rs.next()) {
        print("" + rs.getInt(1) + "\t")
        println(rs.getString(2))
    }
    comando.close()
    conexion.close()
}