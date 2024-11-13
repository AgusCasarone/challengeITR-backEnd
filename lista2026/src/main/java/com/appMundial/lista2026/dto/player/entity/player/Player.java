package com.appMundial.lista2026.dto.player.entity.player;

import com.appMundial.lista2026.dto.player.entity.roster.Roster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String lastName;
    private Integer age;
    private String team;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "Position", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private List<Position> position;
    private Integer number;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "table_id",
            joinColumns = @JoinColumn(name = "id_player"),
            inverseJoinColumns = @JoinColumn(name = "id_roster")
    )
    @JsonIgnore
    private List<Roster> rosters;


    public Player() {
    }

    public Player(Integer id, String name, String lastName, Integer age, String team, List<Position> position, Integer number) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
        this.position = position;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<Position> getPosicion() {
        return position;
    }

    public void setPosicion(List<Position> position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Roster> getRosters() {
        return rosters;
    }

    public void setRosters(List<Roster> rosters) {
        this.rosters = rosters;
    }
}
