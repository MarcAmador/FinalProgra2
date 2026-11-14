package com.examen.apilogin.service;

import com.examen.apilogin.dao.UsuarioDao;
import com.examen.apilogin.model.LoginRequest;
import com.examen.apilogin.model.LoginResponse;
import com.examen.apilogin.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final int CONSTANTE_CODIGO = 5;

    private final UsuarioDao usuarioDao;

    public AuthService(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public LoginResponse autenticar(LoginRequest request) {
        if (request == null || request.getUsuario() == null || request.getPassword() == null
                || request.getUsuario().trim().isEmpty() || request.getPassword().trim().isEmpty()) {
            return new LoginResponse(false, "Usuario o contraseña incorrectos");
        }

        String usuarioRecibido = request.getUsuario().trim();
        String passwordCodificada = request.getPassword().trim();

        String passwordDecodificada;
        try {
            passwordDecodificada = decodificarPassword(passwordCodificada);
        } catch (IllegalArgumentException e) {
            return new LoginResponse(false, "Usuario o contraseña incorrectos");
        }

        System.out.println("\nUsuario: " + usuarioRecibido);
        System.out.println("Password codificada: " + passwordCodificada);
        System.out.println("Password decodificada: " + passwordDecodificada);

        Usuario usuario = usuarioDao.validarCredenciales(usuarioRecibido, passwordDecodificada);

        if (usuario != null) {
            return new LoginResponse(
                    true,
                    "Usuario autenticado correctamente",
                    usuario.getUsuario(),
                    usuario.getNombre()
            );
        }

        return new LoginResponse(false, "Usuario o contraseña incorrectos");
    }

    public String decodificarPassword(String passwordCodificada) {
        String[] partes = passwordCodificada.split("-");
        StringBuilder passwordOriginal = new StringBuilder();

        for (String parte : partes) {
            int valorCodificado = Integer.parseInt(parte.trim());
            int valorAsciiOriginal = valorCodificado - CONSTANTE_CODIGO;
            char caracter = (char) valorAsciiOriginal;
            passwordOriginal.append(caracter);
        }

        return passwordOriginal.toString();
    }
}
