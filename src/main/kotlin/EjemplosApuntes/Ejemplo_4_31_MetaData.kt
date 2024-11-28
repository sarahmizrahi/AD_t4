package org.example.EjemplosApuntes

import java.io.BufferedReader
import java.io.InputStreamReader
import java.sql.DriverManager


    //Obtener y mostrar METADATOS

fun main() {

    //Conectar a la base de datos geo_ad
    val conexion = DriverManager.getConnection ("jdbc:postgresql://89.36.214.106:5432/geo_ad", "geo_ad", "geo_ad")

    //Obtener los metadatos de la conexion
    val dbmd = conexion.getMetaData ()
    //Mostrar por pantalla
    println("Informació general")
    println("------------------")
    println("SGBD " + dbmd.getDatabaseProductName())    //Nombre de la base de datos
    println("Driver " + dbmd.getDriverName())   //Nombre del driver
    println("URL " + dbmd.getURL()) //URL
    println("Usuari " + dbmd.getUserName())     //Usuario
    println()
    println("Llistat de taules")

    //Encabezado para la lista de tablas
    println(String.format("%-6s %-7s %-7s %-10s %-10s", "Número", "Catàleg", "Esquema", "Nom", "Tipus"))
    println("---------------------------------------------")

    //Obtener la lista de tablas
    val lista = dbmd.getTables(null, "public", null, null)
    var contador = 1    // Contador para enumerar las tablas
    val taules = ArrayList<String>()// Lista para almacenar los nombres de las tablas

    // Iterar sobre las tablas y mostrarlas
    while (lista.next()) {
        println(String.format("%-6d %-7s %-7s %-10s %-10s",(contador++),lista.getString(1),lista.getString(2),lista.getString(3),lista.getString(4)))
        taules.add(lista.getString(3))  // Agregar el nombre de la tabla a la lista
    }
    println()   //Salto de linea

    //Entrada de datos
    println("Introdueix un número per veure l'estructura de la taula (0 per acabar) ")
    val ent = BufferedReader(InputStreamReader (System.`in`))
    var opcio = Integer.parseInt(ent.readLine())    // Leer opción introducida

    // Bucle para permitir al usuario ver la estructura de las tablas
    while (opcio != 0) {
        // Comprobar si la opción es válida
        if (opcio < contador && opcio > 0) {
            val taula = dbmd.getTables (null, "public", taules.get(opcio-1), null)  // Obtener información sobre la tabla seleccionada
            if (taula.next()) {
                if (taula.getString(4).equals("TABLE")) {   // Verificar si es una tabla
                    val rs = dbmd.getColumns (null, "public", taules.get(opcio-1), null)    // Obtener las columnas de la tabla
                    println("Estructura de la taula " + taules.get(opcio - 1))  // Mostrar nombre de la tabla
                    println("----------------------------")

                    // Mostrar las columnas y su tipo
                    while (rs.next())
                        println(rs.getString(4) + " (" + rs.getString(6) + ")")
                    println("----------------------------")

                    // Obtener y mostrar las claves primarias de la tabla
                    val rs2 = dbmd.getPrimaryKeys(null, "public", taules.get(opcio - 1))
                    print("Clau principal ")
                    while (rs2.next())
                        print(rs2.getString(4) + " ")   // Imprimir clave primaria
                    println()

                    // Obtener y mostrar las claves externas de la tabla
                    val rs3 = dbmd.getImportedKeys(null, "public", taules.get(opcio - 1))
                    println("Claus externes ")
                    while (rs3.next()) {
                        println(rs3.getString(8) + " apunta a " + rs3.getString(3)) // Imprimir clave externa y la tabla a la que apunta
                    }
                    rs.close()
                    rs2.close()
                    rs3.close()
                }
            }
            taula.close()

        }
        println()
        println("Introdueix un número per veure l'estructura de la taula (0 per acabar) ")
        opcio = Integer.parseInt(ent.readLine())    // Leer la siguiente opción del usuario
    }
    lista.close()
    conexion.close()
}