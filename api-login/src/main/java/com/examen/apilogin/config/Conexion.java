package com.examen.apilogin.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USUARIO = "SYSTEM";
    private static final String PASSWORD = "Amad0r_CRZ";

    public Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con Oracle XE: " + e.getMessage(), e);
        }
    }
}
