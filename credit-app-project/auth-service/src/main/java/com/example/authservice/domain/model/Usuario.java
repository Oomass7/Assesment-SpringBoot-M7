package com.example.authservice.domain.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio Usuario (independiente de frameworks)
 */
public class Usuario {
    private Long id;
    private String email;
    private String password;
    private String nombre;
    private String rol;
    private LocalDateTime fechaCreacion;
    private boolean activo;

    // Constructor
    public Usuario() {
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    public Usuario(String email, String password, String nombre, String rol) {
        this();
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Métodos de negocio
    public boolean esValido() {
        return email != null && !email.isEmpty() 
            && password != null && password.length() >= 6
            && nombre != null && !nombre.isEmpty();
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
