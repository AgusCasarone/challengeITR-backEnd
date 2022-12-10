package com.appMundial.lista2026.controller.jugador;

import com.appMundial.lista2026.dto.jugador.JugadorDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.exception.MissingValuesException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.service.jugador.JugadorService;
import com.appMundial.lista2026.service.jugador.impl.JugadorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jugadores")
@CrossOrigin("*")
public class JugadorController {

    private static final Logger LOGGER = LogManager.getLogger(JugadorController.class);

    @Autowired
    private JugadorServiceImpl jugadorService;

    @PostMapping
    public ResponseEntity<JugadorDto> addJugador(@RequestBody JugadorDto jugadorDto) throws MissingValuesException, Exception {

        return ResponseEntity.ok(jugadorService.parseJugadorEntityToDto(jugadorService.addJugador(jugadorDto)));
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Jugador>> findJugadorById(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(jugadorService.findJugadorById(id));
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<Jugador> updateJugador(@PathVariable Integer id, @RequestBody JugadorDto jugadorDto) throws MissingValuesException, Exception {
        return ResponseEntity.ok(jugadorService.updateJugador(jugadorDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJugador(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        jugadorService.deleteJugadorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Se eliminó el jugador con id %s", id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Jugador>> findAllJugadores() throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(jugadorService.listAllJugadores());
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllJugadoresExistentes() throws ResourceNotFoundException, Exception {
        jugadorService.deleteAllJugadoresExistentes();
        return ResponseEntity.status(HttpStatus.OK).body("Se eliminaron todos los jugadores");
    }

}
