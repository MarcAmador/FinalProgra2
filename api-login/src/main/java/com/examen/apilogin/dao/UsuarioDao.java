package com.examen.apilogin.dao;

import com.examen.apilogin.config.Conexion;
import com.examen.apilogin.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuarioDao {

    private final Conexion conexion;

    public UsuarioDao() {
        this.conexion = new Conexion();
    }

    public Usuario validarCredenciales(String usuario, String password) {
        String sql = "SELECT ID_USUARIO, USUARIO, PASSWORD, NOMBRE FROM USUARIOS WHERE USUARIO = ? AND PASSWORD = ?";

        try (Connection conn = conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario encontrado = new Usuario();
                    encontrado.setIdUsuario(rs.getInt("ID_USUARIO"));
                    encontrado.setUsuario(rs.getString("USUARIO"));
                    encontrado.setPassword(rs.getString("PASSWORD"));
                    encontrado.setNombre(rs.getString("NOMBRE"));
                    return encontrado;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar la tabla USUARIOS: " + e.getMessage(), e);
        }

        return null;
    }
}

