package com.appMundial.lista2026.entity.roster;

import com.appMundial.lista2026.entity.player.Player;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roster")
public class Roster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CollectionTable(name = "State", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "table_id",
            joinColumns = @JoinColumn(name = "id_roster"),
            inverseJoinColumns = @JoinColumn(name = "id_player")
    )
    private Set<Player> players;
    private String name;

    public Roster(Integer id, State state, Set<Player> players, String name) {
        this.id = id;
        this.state = state;
        this.players = players;
        this.name = name;
    }

    public Roster() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
