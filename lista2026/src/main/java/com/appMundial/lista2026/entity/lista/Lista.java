package com.appMundial.lista2026.entity.lista;

import com.appMundial.lista2026.entity.jugador.Jugador;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CollectionTable(name = "Estado", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tabla_id",
            joinColumns = @JoinColumn(name = "id_lista"),
            inverseJoinColumns = @JoinColumn(name = "id_jugador")
    )
    private Set<Jugador> jugadores;
    private String nombre;

    public Lista(Integer id, Estado estado, Set<Jugador> jugadores, String nombre) {
        this.id = id;
        this.estado = estado;
        this.jugadores = jugadores;
        this.nombre = nombre;
    }

    public Lista() {
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
