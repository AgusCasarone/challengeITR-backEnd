package com.appMundial.lista2026.dto.lista;

import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.entity.lista.Estado;

import java.util.Set;

public class ListaDto {

    private Integer id;
    private Estado estado;
    private Set<Jugador> jugadores;
    private String nombre;


    public ListaDto() {
    }

    public ListaDto(Integer id, Estado estado, Set<Jugador> jugadores, String nombre) {
        this.id = id;
        this.estado = estado;
        this.jugadores = jugadores;
        this.nombre = nombre;
    }

    public ListaDto(Estado estado, Set<Jugador> jugadores, String nombre) {
        this.estado = estado;
        this.jugadores = jugadores;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
