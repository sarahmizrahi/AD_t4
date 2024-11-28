package org.example.util.db

import org.example.Ejercicios.Coordenadas
import org.example.Ejercicios.PuntGeo
import org.example.Ejercicios.Ruta
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class GestionarRutesBD {
    val conexion: Connection

    init {
        // Establece la conexión con la base de datos
        conexion = DriverManager.getConnection("jdbc:sqlite:Rutes.sqlite")

        // Crea las tablas RUTES y PUNTS si no existen
        val CrearTablaRutas = """
            CREATE TABLE IF NOT EXISTS RUTES (
                num_r INTEGER PRIMARY KEY AUTOINCREMENT,
                nom_r TEXT NOT NULL,
                desn INTEGER,
                desn_ac INTEGER
            )
        """.trimIndent()

        val CrearTablaPuntos = """
            CREATE TABLE IF NOT EXISTS PUNTS (
                num_r INTEGER,
                num_p INTEGER,
                nom_p TEXT,
                latitud REAL,
                longitud REAL,
                FOREIGN KEY (num_r) REFERENCES Rutes(num_r)
            )
        """.trimIndent()

        conexion.createStatement().execute(CrearTablaRutas)
        conexion.createStatement().execute(CrearTablaPuntos)
    }

    // Cierra la conexión a la base de datos
    fun close() {
        conexion.close()
    }


    fun inserir(r: Ruta) {
        try {
            val stmtMaxNum = conexion.prepareStatement("SELECT MAX(num_r) FROM Rutes")
            val resultSet = stmtMaxNum.executeQuery()
            val nuevoNumR = (resultSet.getInt(1) + 1)

            // Insertar la ruta en la tabla Rutes
            val insertRuta = "INSERT INTO Rutes (num_r, nom_r, desn, desn_ac)VALUES (?, ?, ?, ?)"
            val stmtInsertRuta = conexion.prepareStatement(insertRuta)
            stmtInsertRuta.setInt(1, nuevoNumR)
            stmtInsertRuta.setString(2, r.nom)
            stmtInsertRuta.setInt(3, r.desnivell)
            stmtInsertRuta.setInt(4, r.desnivellAcumulat)
            stmtInsertRuta.executeUpdate()

            // Insertar cada punto en la tabla PUNTS
            val insertPunt = "INSERT INTO Punts (num_r, num_p, nom_p, latitud, longitud)VALUES (?, ?, ?, ?, ?)"
            val stmtInsertPunt = conexion.prepareStatement(insertPunt)
            r.llistaDePunts.forEachIndexed { index, punt ->
                stmtInsertPunt.setInt(1, nuevoNumR)
                stmtInsertPunt.setInt(2, index + 1)
                stmtInsertPunt.setString(3, punt.nom)
                stmtInsertPunt.setDouble(4, punt.coord.latitud)
                stmtInsertPunt.setDouble(5, punt.coord.longitud)
                stmtInsertPunt.executeUpdate()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    // Busca una ruta por número
    fun buscar(num_r: Int): Ruta? {
        val queryRuta = "SELECT * FROM Rutes WHERE num_r = ?"
        val stmtRuta = conexion.prepareStatement(queryRuta)
        stmtRuta.setInt(1, num_r)
        val resultSetRuta = stmtRuta.executeQuery()

        if (resultSetRuta.next()) {
            val nom = resultSetRuta.getString("nom_r")
            val desnivell = resultSetRuta.getInt("desn")
            val desnivellAcumulat = resultSetRuta.getInt("desn_ac")

            val puntsQuery = "SELECT * FROM Punts WHERE num_r = ? ORDER BY num_p"
            val stmtPunts = conexion.prepareStatement(puntsQuery)
            stmtPunts.setInt(1, num_r)
            val resultSetPunts = stmtPunts.executeQuery()

            val punts = arrayListOf<PuntGeo>()
            while (resultSetPunts.next()) {
                val nomPunt = resultSetPunts.getString("nom_p")
                val latitud = resultSetPunts.getDouble("latitud")
                val longitud = resultSetPunts.getDouble("longitud")
                punts.add(PuntGeo(nomPunt, Coordenadas(latitud, longitud)))
            }

            stmtPunts.close()
            stmtRuta.close()
            return Ruta(nom, desnivell, desnivellAcumulat, punts)
        }
        stmtRuta.close()
        return null
    }

    // Devuelve un listado de todas las rutas
    fun llistat(): ArrayList<Ruta> {
        val rutes = arrayListOf<Ruta>()
        val queryRutes = "SELECT * FROM Rutes"
        val resultSetRutes = conexion.createStatement().executeQuery(queryRutes)

        while (resultSetRutes.next()) {
            val numR = resultSetRutes.getInt("num_r")
            val ruta = buscar(numR)
            if (ruta != null) rutes.add(ruta)
        }
        return rutes
    }

    fun MostrarRutaBD(num_r: Int) {

        val queryRuta = "SELECT * FROM Rutes WHERE num_r = ?"
        val stmtRuta = conexion.prepareStatement(queryRuta)
        stmtRuta.setInt(1, num_r)
        val resultSetRuta = stmtRuta.executeQuery()


        if (resultSetRuta.next()) {

            val nom = resultSetRuta.getString("nom_r")
            val desnivell = resultSetRuta.getInt("desn")
            val desnivellAcumulat = resultSetRuta.getInt("desn_ac")

            println("Ruta ID: $num_r")
            println("Nombre: $nom")
            println("Desnivel: $desnivell")
            println("Desnivel Acumulado: $desnivellAcumulat")

            val puntsQuery = "SELECT * FROM Punts WHERE num_r = ? ORDER BY num_p"
            val stmtPunts = conexion.prepareStatement(puntsQuery)
            stmtPunts.setInt(1, num_r)
            val resultSetPunts = stmtPunts.executeQuery()


            println("Puntos Geográficos:")
            while (resultSetPunts.next()) {
                val nomPunt = resultSetPunts.getString("nom_p")
                val latitud = resultSetPunts.getDouble("latitud")
                val longitud = resultSetPunts.getDouble("longitud")
                println(" - $nomPunt (Latitud: $latitud, Longitud: $longitud)")
            }

            resultSetPunts.close()
            stmtPunts.close()
        } else {
            println("No se encontró ninguna ruta con el ID: $num_r")
        }


        resultSetRuta.close()
        stmtRuta.close()
    }

    // Elimina una ruta por número
    fun esborrar(num_r: Int) {
        try {
            // Borra primero los puntos de la ruta
            val deletePunts = "DELETE FROM Punts WHERE num_r = ?"
            val stmtDeletePunts = conexion.prepareStatement(deletePunts)
            stmtDeletePunts.setInt(1, num_r)
            stmtDeletePunts.executeUpdate()

            // Borra la ruta
            val deleteRuta = "DELETE FROM Rutes WHERE num_r = ?"
            val stmtDeleteRuta = conexion.prepareStatement(deleteRuta)
            stmtDeleteRuta.setInt(1, num_r)
            stmtDeleteRuta.executeUpdate()

            stmtDeletePunts.close()
            stmtDeleteRuta.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}