package com.alexa.mimusica.beltran.model;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Cancion implements Comparable<Cancion> {

    private String titulo;

    private String autor;

    private String disco;

    private String ubicacion;

    private String token;

    private String propietario;

    public Cancion() {

    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("titulo", this.titulo);
        map.put("autor", this.autor);
        map.put("disco", this.disco);
        map.put("ubicacion", this.ubicacion);
        map.put("token", this.token);
        map.put("propietario", this.propietario);

        return map;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public int compareTo(Cancion o) {
        return  this.titulo.compareTo(o.getTitulo());
    }
}
