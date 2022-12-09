package com.appMundial.lista2026.service.jugador.impl;


import com.appMundial.lista2026.controller.jugador.JugadorController;
import com.appMundial.lista2026.dto.jugador.JugadorDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.jugador.JugadorRepository;
import com.appMundial.lista2026.service.jugador.JugadorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class JugadorServiceImpl implements JugadorService {

    private static final Logger LOGGER = LogManager.getLogger(JugadorServiceImpl.class);

    @Autowired
    private JugadorRepository jugadorRepository;

    public Jugador addJugador(JugadorDto jugadorDto) {
        Jugador jugadorEntity = parseJugadorDtoToEntity(jugadorDto);
        LOGGER.info(String.format("Se creó un nuevo jugador:" +
                "\n Nombre: %s " +
                "\n Apellido: %s " +
                "\n Edad: %s " +
                "\n Equipo: %s " +
                "\n Posicion: %s " +
                "\n Numero: %s ", jugadorDto.getNombre(), jugadorDto.getApellido(), jugadorDto.getEdad(), jugadorDto.getEquipo(), jugadorDto.getPosicion(), jugadorDto.getNumero()));
        return jugadorRepository.save(jugadorEntity);
    }

    public Optional<Jugador> findJugadorById(Integer id) {
        LOGGER.info(String.format("Se encontró el jugador con id %s ", id));
        return jugadorRepository.findById(id);
    }

    public Jugador updateJugador(JugadorDto jugadorDtoEddit, Integer id) throws ResourceNotFoundException {

        JugadorDto jugadorDtoOriginal = parseJugadorEntityToDto(jugadorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No se encontró el jugador")));

        if (jugadorDtoEddit.getNombre() != null){
            jugadorDtoOriginal.setNombre(jugadorDtoEddit.getNombre());
        }
        if (jugadorDtoEddit.getApellido() != null){
            jugadorDtoOriginal.setApellido(jugadorDtoEddit.getApellido());
        }
        if (jugadorDtoEddit.getEdad() != null){
            jugadorDtoOriginal.setEdad(jugadorDtoEddit.getEdad());
        }
        if (jugadorDtoEddit.getEquipo() != null){
            jugadorDtoOriginal.setEquipo(jugadorDtoEddit.getEquipo());
        }
        if (jugadorDtoEddit.getPosicion() != null) {
            jugadorDtoOriginal.setPosicion(jugadorDtoEddit.getPosicion());
        }
        if (jugadorDtoEddit.getNumero() != null) {
            jugadorDtoOriginal.setNumero(jugadorDtoEddit.getNumero());
        }

        jugadorDtoOriginal.setId(id);

        LOGGER.info(String.format("Se actualizó el jugador con id %s", id));
        jugadorRepository.save(parseJugadorDtoToEntity(jugadorDtoOriginal));
        return parseJugadorDtoToEntity(jugadorDtoOriginal);

    }

    public boolean deleteJugadorById(Integer id){
        if (jugadorRepository.existsById(id)) {
            LOGGER.info(String.format("Se eliminó el jugador con id %s", id));
            jugadorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Jugador> listAllJugadores() {
        return jugadorRepository.findAll();
    }


    public void deleteAllJugadoresExistentes() {
        LOGGER.info("Se eliminaron todos los jugadores");
        jugadorRepository.deleteAll();
    }

    public Jugador parseJugadorDtoToEntity(JugadorDto jugadorDto) {

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