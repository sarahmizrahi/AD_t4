package org.example.EjemplosApuntes

import java.sql.DriverManager


//CREAR TABLA

fun main() {

    val url = "jdbc:sqlite:Empleats.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()


    val sentSQL = "CREATE TABLE EMPLEAT(" +
            "num INTEGER PRIMARY KEY, " +
            "nom TEXT, " +
            "depart INTEGER, " +
            "edat INTEGER, " +
            "sou REAL " +
            ")"

    st.executeUpdate(sentSQL)
    st.close();
    con.close()
}

