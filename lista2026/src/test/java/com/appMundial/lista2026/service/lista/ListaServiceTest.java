package com.appMundial.lista2026.service.lista;

import com.appMundial.lista2026.dto.lista.ListaDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.entity.lista.Estado;
import com.appMundial.lista2026.entity.lista.Lista;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ListaServiceTest {

    @Autowired
    private ListaService listaService;

    @BeforeEach


    @SneakyThrows
    @Test
    void addLista() {
        //Arrange
        Jugador jugador = new Jugador();
        Set<Jugador> jugadores = new HashSet<>(List.of(jugador));
        ListaDto listaDto = new ListaDto(Estado.ENPROCESO, jugadores, "lista1");

        //Act
        Lista result;
        result = listaService.addLista(listaDto);

        //Assert
        assertNotNull(result);
    }



    @Test
    void findListaById() {
        //Arrange
        Integer id = 12;
        Set<Jugador> jugadores = (Set<Jugador>) new Jugador();
        ListaDto listaDto = new ListaDto(12, Estado.ENPROCESO, jugadores, "lista1");

        //Act
        listaService.findListaById(id);

        //Assert
        assertEquals(id, listaDto.getId());

    }

    @SneakyThrows
    @Test
    void deleteListaById() {
        //Arrange
        Integer id = 12;
        Set<Jugador> jugadores = (Set<Jugador>) new Jugador();
        ListaDto listaDto = new ListaDto(12, Estado.ENPROCESO, jugadores, "lista1");
        Lista lista = listaService.addLista(listaDto);

        //Act
        listaService.deleteListaById(id);

        //Assert
        Optional<Lista> result = listaService.findListaById(id);
        Assertions.assertFalse(result.isPresent());
    }

    @SneakyThrows
    @Test
    void addJugadorALista(){
        //Arrange
        Jugador jugador = new Jugador();
        Jugador jugador2 = new Jugador();
        Set<Jugador> jugadores = new HashSet<>(List.of(jugador));
        ListaDto listaDto = new ListaDto(Estado.ENPROCESO, jugadores, "lista1");

        //Act
        Lista result;
        result = listaService.addLista(listaDto);
        listaService.addJugadorALista(listaDto.getId(), jugador2.getId());

        //Assert
        assertNotEquals(listaDto.getJugadores().size(), result.getJugadores().size());

    }

}
