package org.example.Ejercicios

import java.io.*
import java.sql.*


fun main(args: Array<String>) {

    //Crear conexion e instrucciones
    val url = "jdbc:sqlite:rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val stRuta = con.prepareStatement("INSERT INTO RUTAS VALUES (?, ?, ?, ?)")
    val stPunto = con.prepareStatement("INSERT INTO PUNTOS VALUES (?, ?, ?, ?, ?)")

    //Leer Rutes.obj
    val f = ObjectInputStream(FileInputStream("Rutes.obj"))

    try {
        var num_r = 1
        while(true) {
            val r = f.readObject() as Ruta
            //Insertar datos
            stRuta.setInt(1, num_r)
            stRuta.setString(2, r.nom)
            stRuta.setInt(3, r.desnivell)
            stRuta.setInt(4, r.desnivellAcumulat)
            stRuta.executeUpdate()

            //insertar los PUNTOS
            for(num_p in 0 until r.llistaDePunts.size) {
                stPunto.setInt(1, num_r)
                stPunto.setInt(2, num_p)
                stPunto.setString(3, r.getPuntNom(num_p))
                stPunto.setDouble(4, r.getPuntLatitud(num_p))
                stPunto.setDouble(5, r.getPuntLongitud(num_p))
                stPunto.executeUpdate()
            }
            num_r++
        }
    } catch (e: EOFException) {
        e.message
    }

    f.close()
    stPunto.close()
    stRuta.close()
    con.close()
}
