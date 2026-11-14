package com.examen.apilogin.model;

public class LoginResponse {

    private boolean autenticado;
    private String mensaje;
    private String usuario;
    private String nombre;

    public LoginResponse() {
    }

    public LoginResponse(boolean autenticado, String mensaje) {
        this.autenticado = autenticado;
        this.mensaje = mensaje;
    }

    public LoginResponse(boolean autenticado, String mensaje, String usuario, String nombre) {
        this.autenticado = autenticado;
        this.mensaje = mensaje;
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
