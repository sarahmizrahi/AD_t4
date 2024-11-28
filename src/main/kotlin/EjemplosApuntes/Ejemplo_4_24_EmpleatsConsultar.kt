package org.example.EjemplosApuntes

import java.sql.DriverManager


//mostrar por pantalla todos los empleados de lo que cobran más de 1.100€

fun main() {

    val url = "jdbc:sqlite:Empleats.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()

    val sentenciaSQL = "SELECT * FROM EMPLEAT WHERE sou > 1100"
    val rs = st.executeQuery(sentenciaSQL)

    System.out.println("Núm. \tNom \tDep \tEdat \tSou")
    System.out.println("-----------------------------------------")

    while (rs.next()) {
        print("" + rs.getInt(1) + "\t")
        print(rs.getString(2) + "\t")
        print("" + rs.getInt(3) + "\t")
        print("" + rs.getInt(4) + "\t")
        println(rs.getDouble(5))
    }

    rs.close()
    st.close()
    con.close()
}