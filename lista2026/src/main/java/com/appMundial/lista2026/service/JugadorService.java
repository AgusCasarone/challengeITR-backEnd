package com.appMundial.lista2026.service;


import com.appMundial.lista2026.dto.JugadorDto;
import com.appMundial.lista2026.entity.Jugador;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public Jugador addJugador(JugadorDto jugadorDto) {
        Jugador jugadorEntity = parseJugadorDtoToEntity(jugadorDto);
        return jugadorRepository.save(jugadorEntity);
    }

    public Optional<Jugador> findJugadorById(Integer id) {
        return jugadorRepository.findById(id);
    }

    public boolean deleteJugadorById(Integer id){
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Jugador> listAllJugadores() {
        return jugadorRepository.findAll();
    }

    private Jugador parseJugadorDtoToEntity(JugadorDto jugadorDto) {

        Jugador jugadorEntity = new Jugador();

        jugadorEntity.setId(jugadorDto.getId());
        jugadorEntity.setNombre(jugadorDto.getNombre());
        jugadorEntity.setApellido(jugadorDto.getApellido());
        jugadorEntity.setEdad(jugadorDto.getEdad());
        jugadorEntity.setEquipo(jugadorDto.getEquipo());
        jugadorEntity.setEquipo(jugadorDto.getEquipo());
        jugadorEntity.setPosicion(jugadorDto.getPosicion());
        jugadorEntity.setNumero(jugadorDto.getNumero());

        return jugadorEntity;
    }

    public JugadorDto parseJugadorEntityToDto(Jugador jugadorEntity) {

        JugadorDto jugadorDto = new JugadorDto();

        jugadorDto.setId(jugadorEntity.getId());
        jugadorDto.setNombre(jugadorEntity.getNombre());
        jugadorDto.setApellido(jugadorEntity.getApellido());
        jugadorDto.setEdad(jugadorEntity.getEdad());
        jugadorDto.setEquipo(jugadorEntity.getEquipo());
        jugadorDto.setPosicion(jugadorEntity.getPosicion());
        jugadorDto.setNumero(jugadorEntity.getNumero());

        return jugadorDto;
    }

}