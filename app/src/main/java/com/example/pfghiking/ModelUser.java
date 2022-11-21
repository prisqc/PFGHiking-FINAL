package com.example.pfghiking;

public class ModelUser {
    String id;
    String email;
    String nombre;
    String foto;


    public ModelUser(){}

    public ModelUser(int id, String email, String nombre) {
    }

    public ModelUser(String id, String email, String nombre, String foto) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.foto = foto;

    }

    public ModelUser(String id, String email, String nombre) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;

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
        return  id;
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
