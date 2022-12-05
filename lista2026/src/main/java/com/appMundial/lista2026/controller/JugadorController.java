package com.appMundial.lista2026.controller;

import com.appMundial.lista2026.dto.JugadorDto;
import com.appMundial.lista2026.entity.Jugador;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.service.JugadorService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    private static final Logger LOGGER = LogManager.getLogger(JugadorController.class);

    @Autowired
    private JugadorService jugadorService;

    // Create
    @PostMapping
    public ResponseEntity<JugadorDto> addJugador(@RequestBody JugadorDto jugadorDto) throws ResourceNotFoundException, Exception {
        LOGGER.info(String.format("Se creó un nuevo jugador:" +
                "\n Nombre: %s " +
                "\n Apellido: %s " +
                "\n Edad: %s " +
                "\n Equipo: %s " +
                "\n Posicion: %s " +
                "\n Numero: %s ", jugadorDto.getNombre(), jugadorDto.getApellido(), jugadorDto.getEdad(), jugadorDto.getEquipo(), jugadorDto.getPosicion(), jugadorDto.getNumero()));
        return ResponseEntity.ok(jugadorService.parseJugadorEntityToDto(jugadorService.addJugador(jugadorDto)));
    }

    // Read
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Jugador>> findJugadorById(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        LOGGER.info(String.format("Se encontró el jugador con id %s ", id));
        return ResponseEntity.ok(jugadorService.findJugadorById(id));
    }

    // Update
    @PutMapping (value = "/{id}")
    public ResponseEntity<Jugador> updateJugador(@PathVariable Integer id, @RequestBody JugadorDto jugadorDto) throws ResourceNotFoundException, Exception {
        LOGGER.info(String.format("Se actualizó el jugador con id %s", id));
        jugadorDto.setId(id);
        return ResponseEntity.ok(jugadorService.addJugador(jugadorDto));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        LOGGER.info(String.format("Se eliminó el jugador con id %s", id));
        jugadorService.deleteJugadorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Se eliminó el jugador con id %s", id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Jugador>> findAllJugadores() throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(jugadorService.listAllJugadores());
    }

}
