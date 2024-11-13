package com.appMundial.lista2026.dto.roster;

import com.appMundial.lista2026.dto.player.entity.roster.State;
import com.appMundial.lista2026.dto.player.entity.player.Player;

import java.util.Set;

public class RosterDto {

    private Integer id;
    private State state;
    private Set<Player> players;
    private String name;


    public RosterDto() {
    }

    public RosterDto(Integer id, State state, Set<Player> players, String name) {
        this.id = id;
        this.state = state;
        this.players = players;
        this.name = name;
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
