package com.appMundial.lista2026.service.jugador;

import com.appMundial.lista2026.dto.jugador.JugadorDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface JugadorService {
    public Jugador addJugador(JugadorDto jugadorDto);

    public Optional<Jugador> findJugadorById(Integer id);

    public Jugador updateJugador(JugadorDto jugadorDtoEddit, Integer id)throws ResourceNotFoundException;

    public boolean deleteJugadorById(Integer id);

    public List<Jugador> listAllJugadores();

    public void deleteAllJugadoresExistentes();

    public Jugador parseJugadorDtoToEntity(JugadorDto jugadorDto);

    public JugadorDto parseJugadorEntityToDto(Jugador jugadorEntity);

    }
