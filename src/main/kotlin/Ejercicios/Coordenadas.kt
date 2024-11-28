package org.example.Ejercicios

import java.io.*

data class Coordenadas(
    var latitud: Double,
    var longitud: Double): Serializable {
        companion object {
            private const val serialVersionUID: Long = 1
        }

}