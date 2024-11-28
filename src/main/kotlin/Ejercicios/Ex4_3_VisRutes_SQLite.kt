package org.example.Ejercicios

import javax.swing.JFrame
import java.awt.EventQueue
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.FlowLayout
import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.sql.DriverManager
import javax.swing.JComboBox
import javax.swing.JButton
import javax.swing.JTextArea
import javax.swing.JLabel

class Finestra : JFrame() {

    init {
        // Sentències per a fer la connexió
        val url = "jdbc:sqlite:Rutes.sqlite"
        val con = DriverManager.getConnection(url)


        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("JDBC: Visualitzar Rutes")
        setSize(450, 450)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(BorderLayout())
        add(panell1, BorderLayout.NORTH)
        add(panell2, BorderLayout.CENTER)

        val llistaRutes = arrayListOf<String>()
        // Sentències per a omplir l'ArrayList amb el nom de les rutes
        val f = ObjectInputStream(FileInputStream("Rutes.obj"))
        try {
            while (true) {
                val r = f.readObject() as Ruta
                llistaRutes.add(r.nom)
            }
        }catch (e: EOFException) {
            e.message
        }

        val combo = JComboBox<String>(llistaRutes.toTypedArray())
        panell1.add(combo)
        val eixir = JButton("Eixir")
        panell1.add(eixir)
        val area = JTextArea()
        panell2.add(JLabel("Llista de punts de la ruta:"),BorderLayout.NORTH)
        panell2.add(area,BorderLayout.CENTER)


        combo.addActionListener() {
            // Sentèncis quan s'ha seleccionat un element del JComboBox
            // Han de consistir en omplir el JTextArea
            val selec = combo.selectedIndex + 1
            val stPuntos = con.prepareStatement("select * from PUNTOS where num_r = ?")
            stPuntos.setInt(1, selec)
            val rs = stPuntos.executeQuery()
            area.text = ""  //Vaciar JTextArea
            while (rs.next()) {
                val nomPunto = rs.getString("nombre_p")
                val latitud = rs.getDouble("latitud")
                val longitud = rs.getDouble("longitud")
                area.append("$nomPunto ($latitud,$longitud)\n")
            }
            rs.close()
            stPuntos.close()
        }

        eixir.addActionListener(){
            // Sentències per a tancar la connexió i eixir
            con.close()
            System.exit(0)
        }
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        Finestra().isVisible = true
    }
}