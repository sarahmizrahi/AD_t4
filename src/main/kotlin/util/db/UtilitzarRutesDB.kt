package org.example.util.db

import org.example.Ejercicios.Coordenadas
import org.example.Ejercicios.PuntGeo
import org.example.Ejercicios.Ruta
import java.util.*

fun main() {
    // Creació del gestionador
    val gRutes = GestionarRutesBD()

    val noms = arrayOf("Les Useres", "Les Torrocelles", "Lloma Bernat", "Xodos (Molí)", "El Marinet", "Sant Joan")
    val latituds = arrayOf(40.158126, 40.196046, 40.219210, 40.248003, 40.250977, 40.251221)
    val longituds = arrayOf(-0.166962, -0.227611, -0.263560, -0.296690, -0.316947, -0.354052)


    val punts = arrayListOf<PuntGeo>()
    for (i in 0 until 6) {
        punts.add(PuntGeo(noms[i], Coordenadas(latituds[i], longituds[i])))
    }


    while (true) {
        println("Menú de opciones:")
        println("1. Listar todas las rutas")
        println("2. Buscar una ruta")
        println("3. Insertar una ruta")
        println("4. Eliminar una ruta")
        println("5. Salir")
        print("Selecciona una opción: ")

        val opcion= readln().toInt()

        when (opcion) {
            1 -> {
                val rutes=gRutes.llistat()
                println(" ")
                for (ruta in rutes) {
                    println("Nom:  ${ruta.nom}")
                    println("Desnivell:  ${ruta.desnivell}")
                    println("Desnivel Acumulado:  ${ruta.desnivellAcumulat}")
                    println("Puntos Geográficos: ")

                    for (punto in ruta.llistaDePunts) {
                        println(" - ${punto.nom} (Latitud: ${punto.coord.latitud}, Longitud: ${punto.coord.longitud})")
                    }
                    println()
                }
            }

            2 -> {
                print("Introduce el ID de la ruta a buscar: ")
                val id = readln().toInt()
                val ruta = gRutes.buscar(id)
                if (ruta != null) {
                    println("Ruta encontrada:")
                    gRutes.MostrarRutaBD(id)
                } else {
                    println("Ruta no encontrada.")
                }
            }

            3 -> gRutes.inserir(Ruta("Pelegrins de Les Useres", 896, 1738, punts))
            4 -> {
                print("Introduce el numero de la ruta a eliminar: ")
                val id = readln().toInt()
                if (id != null) {
                    gRutes.esborrar(id)
                    println("Ruta y puntos eliminados.")
                } else {
                    println("ID inválido.")
                }
            }
            5 -> {
                gRutes.close()
                println("Saliendo del programa")
                return
            }

            else -> println("Opción inválida.")
        }
    }
}