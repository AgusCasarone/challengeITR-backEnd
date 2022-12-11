package com.appMundial.lista2026.entity.jugador;

import com.appMundial.lista2026.entity.lista.Lista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "jugador")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String equipo;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Posicion", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private List<Posicion> posicion;
    private Integer numero;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tabla_id",
            joinColumns = @JoinColumn(name = "id_jugador"),
            inverseJoinColumns = @JoinColumn(name = "id_lista")
    )
    @JsonIgnore
    private List<Lista> listas;


    public Jugador() {
    }

    public Jugador(Integer id, String nombre, String apellido, Integer edad, String equipo, List<Posicion> posicion, Integer numero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.equipo = equipo;
        this.posicion = posicion;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public List<Posicion> getPosicion() {
        return posicion;
    }

    public void setPosicion(List<Posicion> posicion) {
        this.posicion = posicion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }
}
