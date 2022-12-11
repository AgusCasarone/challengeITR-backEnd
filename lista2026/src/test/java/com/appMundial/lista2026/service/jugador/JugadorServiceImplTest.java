package com.appMundial.lista2026.service.jugador;

import com.appMundial.lista2026.dto.jugador.JugadorDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.service.jugador.impl.JugadorServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.appMundial.lista2026.entity.jugador.Posicion.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class JugadorServiceImplTest {

    @Autowired
    private JugadorServiceImpl jugadorService;

    @Test
    void addJugador() {
        //Arrange
        JugadorDto jugadorDto = new JugadorDto("Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        Jugador result = jugadorService.addJugador(jugadorDto);

        //Assert
        assertNotNull(result);
    }

    @Test
    void findJugadorById() {
        //Arrange
        Integer id = 12;
        JugadorDto jugadorDto = new JugadorDto(id, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        jugadorService.findJugadorById(jugadorDto.getId());

        //Assert
        assertEquals(id, jugadorDto.getId());
    }

    @SneakyThrows
    @Test
    void updateJugador() {
        //Arrange
        JugadorDto jugadorDto = new JugadorDto("Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        JugadorDto jugadorDto1 = new JugadorDto("Lautaro", "Martinez", 23, "Inter", java.util.List.of(VOLANTE, ARQUERO), 13);

        //Act
        jugadorService.updateJugador(jugadorDto, jugadorDto1.getId());

        //Assert
        assertNotEquals(jugadorDto1.getId(), jugadorDto.getId());
    }

    @Test
    void deleteJugadorByIdTrue() {
        //Arrange
        JugadorDto jugadorDto = new JugadorDto("Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Jugador jugadorGuardado = jugadorService.addJugador(jugadorDto);

        //Act
        jugadorService.deleteJugadorById(jugadorGuardado.getId());

        //Assert
        Optional<Jugador> result = jugadorService.findJugadorById(jugadorGuardado.getId());
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deleteJugadorByIdFalse() {
        //Arrange
        JugadorDto jugadorDto = new JugadorDto(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Jugador jugadorGuardado = jugadorService.addJugador(jugadorDto);

        //Act
        jugadorService.deleteJugadorById(2);

        //Assert
        assertFalse(jugadorService.findJugadorById(1).isPresent());
    }

    @Test
    void parseJugadorDtoToEntity(){
        //Arrange
        JugadorDto jugadorDto = new JugadorDto(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        Jugador jugador = jugadorService.parseJugadorDtoToEntity(jugadorDto);

        //Assert
        assertEquals(jugador.getEdad(), jugadorDto.getEdad());
    }

    @Test
    void parseJugadorEntityToDto(){
        //Arrange
        Jugador jugador = new Jugador(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        JugadorDto jugadorDto = jugadorService.parseJugadorEntityToDto(jugador);

        //Assert
        assertEquals(jugador.getEdad(), jugadorDto.getEdad());

    }


}
