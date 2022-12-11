package com.appMundial.lista2026.dto.player;

import com.appMundial.lista2026.entity.player.Position;

import java.util.List;

public class PlayerDto {

    public Integer id;
    public String name;
    public String lastName;
    public Integer age;
    public String team;
    public List<Position> position;
    public Integer number;

    public PlayerDto() {
    }

    public PlayerDto(String name, String lastName, Integer age, String team, List<Position> position, Integer number) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
        this.position = position;
        this.number = number;
    }

    public PlayerDto(Integer id, String name, String lastName, Integer age, String team, List<Position> position, Integer number) {
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

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

