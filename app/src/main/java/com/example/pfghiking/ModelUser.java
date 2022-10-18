package com.example.pfghiking;

public class ModelUser {
    String id;
    String email;
    String nombre;
    String foto;


    public ModelUser() {
    }

    public ModelUser(String id, String email, String nombre, String foto) {
        this.email = email;
        this.nombre = nombre;
        this.id = id;
        this.foto = foto;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;  }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
