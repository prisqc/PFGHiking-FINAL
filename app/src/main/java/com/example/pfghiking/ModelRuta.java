package com.example.pfghiking;

import android.widget.Button;

import java.io.Serializable;

public class ModelRuta  implements Serializable {

        String id;
        String nombre_ruta;
        String descripcion;
        String distancia;
        String desnivel;
        String tiempo;
        String pais;
        String ciudad;
        ModelUser users;
        String imagen;
        Button dateButton;



        public ModelRuta(String title, ModelUser usuario, String description, String distancia, String desnivel, String time, String pais, String city, String urlImagen, Button dateButton) {
        }

    public ModelRuta(String nombre_ruta, ModelUser users, String distancia,
                     String desnivel, String tiempo) {
        this.nombre_ruta = nombre_ruta;
        this.users = users;
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.tiempo = tiempo;

    }


    public ModelRuta(String nombre_ruta, ModelUser users, String descripcion, String distancia,
                     String desnivel, String tiempo, String pais, String ciudad, Button dateButton) {
        this.nombre_ruta = nombre_ruta;
        this.users = users;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.tiempo = tiempo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.imagen = imagen;
        this.dateButton = dateButton;
    }


    public ModelRuta(String id, String nombre_ruta, ModelUser users, String distancia,
                     String desnivel, String tiempo, String imagen) {
        this.id = id;
        this.nombre_ruta = nombre_ruta;
        this.users = users;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.tiempo = tiempo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.imagen = imagen;

    }

    public ModelRuta(String nombre_ruta, ModelUser users, String descripcion, String distancia,
                     String desnivel, String pais, String ciudad, String tiempo, String imagen) {

        this.nombre_ruta = nombre_ruta;
        this.users = users;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.desnivel = desnivel;
        this.tiempo = tiempo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.imagen = imagen;

    }


        public String getId() {
        return id;
    }

        public void setId(String id) {
        this.id = id;
    }

        public String getNombre_ruta() {
            return nombre_ruta;
        }

        public void setNombre_ruta(String nombre_ruta) {
            this.nombre_ruta = nombre_ruta;         }

        public ModelUser getUsers(){ return users; }

        public void setUsers(ModelUser users) {
        this.users = users;
    }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDistancia() {
            return distancia;
        }

        public void setDistancia(String distancia) {
            this.distancia = distancia;
        }

        public String getDesnivel() {
            return desnivel;
        }

        public void setDesnivel(String desnivel) {
            this.desnivel = desnivel;
        }

        public String getTiempo() {
            return tiempo;
        }

        public void setTiempo(String tiempo) {
            this.tiempo = tiempo;
        }

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

    public Button getDateButton() {
        return dateButton;
    }

    public void setDateButton(Button dateButton) {
        this.dateButton = dateButton;
    }
}