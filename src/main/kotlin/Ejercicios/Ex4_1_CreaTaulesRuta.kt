package org.example.Ejercicios

import java.sql.DriverManager

fun main(args: Array<String>) {

    //Crear variable para almacenar la url para la conexion a la BBDD "rutes.sqlite". Si el fichero no existe, lo crea
    val url = "jdbc:sqlite:rutes.sqlite"

    //Conectar a rutes.sqlite
    val con = DriverManager.getConnection(url)

    //Crea objeto Statement para las instrucciones
    val comandos = con.createStatement()

    comandos.executeUpdate("Create table IF NOT EXISTS Rutes(" +
            "num_r  integer primary key," + //Llave principal
            "nom_r text," +
            "desn integer," +
            "desn_ac integer)")


    comandos.executeUpdate("CREATE table IF NOT EXISTS Punts(" +
            "num_r integer," +
            "num_p integer," +
            "nom_p  text," +
            "latitud real," +
            "longitud real," +
            //Llave externa num_r que apunta a la principal de la tabla rutes, num_r.
            //Se especifica borrar en cascada por si se borra una fila de Rutes, tambien lo hará de Punts
            "foreign key (num_r) references Rutes(num_r) on delete cascade )")


    comandos.close()  //Cierra la instruccion
    con.close()    //Cierra la conexion
}