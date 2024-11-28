package org.example.Ejercicios

import java.io.*

data class Ruta (
    var nom: String,
    var desnivell: Int,
    var desnivellAcumulat: Int,
    var llistaDePunts: MutableList<PuntGeo>): Serializable {
        companion object {
            private const val serialVersionUID: Long = 1
        }

    //Agregar nuevo punto a la lista
    fun addPunt(p: PuntGeo){
        llistaDePunts.add(p)
    }

    //Se le pasa el indice que se quiere obtener y lo devuelve
    fun getPunt(i: Int): PuntGeo{
        return llistaDePunts.get(i)
    }

    //Se le pasa el indice del nombre que se quiere obtener y lo devuelve
    fun getPuntNom(i: Int): String {
        return llistaDePunts.get(i).nom
    }

    //Se le pasa el indice de la latitud que se quiere obtener y la devuelve
    fun getPuntLatitud(i: Int): Double {
        return llistaDePunts.get(i).coord.latitud
    }

    //Se le pasa el indice la longitud que se quiere obtener y la devuelve
    fun getPuntLongitud(i: Int): Double {
        return llistaDePunts.get(i).coord.longitud
    }

    //Da el numero de puntos guardados en la lista
    fun size(): Int {
        return llistaDePunts.size
    }

    //Mi implementacion
    fun mostrarRuta() {
            //Mostrar los datos desde las variables existentes de propia clase ruta
            println("Ruta: $nom")
            println("Desnivel: $desnivell")
            println("Desnivel Acumulado: $desnivellAcumulat")
            println("Tiene ${llistaDePunts.size} puntos")	//Usar la funcion size que ya estaba hecha

            //Recorrer y mostrar los puntos
            for (i in 0 until llistaDePunts.size) {	//Los recorre desde 0 hasta el tamaño total de la lista
                val punto = llistaDePunts[i]
                val latitud = punto.coord.latitud
                val longitud = punto.coord.longitud
                println("Punto ${i + 1}: ${punto.nom} ($latitud, $longitud)")
            }
        }
}