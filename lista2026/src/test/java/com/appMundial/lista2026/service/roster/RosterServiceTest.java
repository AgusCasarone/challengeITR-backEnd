package com.appMundial.lista2026.service.roster;

import com.appMundial.lista2026.dto.roster.RosterDto;
import com.appMundial.lista2026.dto.player.entity.roster.Roster;
import com.appMundial.lista2026.dto.player.entity.player.Player;
import com.appMundial.lista2026.repository.roster.RosterRepository;
import com.appMundial.lista2026.service.roster.impl.RosterServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.appMundial.lista2026.dto.player.entity.roster.State.ENPROCESO;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RosterServiceTest {

    @InjectMocks
    private RosterServiceImpl rosterService;

    @Mock
    private RosterRepository rosterRepository;

    @SneakyThrows
    @Test
    void shouldAddRosterSuccessfully() {
        //Arrange
        Player player = new Player();
        Set<Player> players = new HashSet<>(List.of(player));
        Roster spected = new Roster(1, ENPROCESO, players, "lista1");
        Mockito.when(rosterRepository.save(Mockito.any()))
                .thenReturn(spected);

        //Act
        final Roster result = rosterService.addRoster(rosterService.parseRosterEntityToDto(spected));

        //Assert
        assertEquals(spected.getId(), result.getId());
        assertEquals(spected.getState(), result.getState());
        assertEquals(spected.getPlayers(), result.getPlayers());
        assertEquals(spected.getName(), result.getName());
    }

    @SneakyThrows
    @Test
    void shouldFindRosterByIdSuccessfully() {
        //Arrange
        Integer id = 1;
        Player player = new Player();
        Set<Player> players = new HashSet<>(List.of(player));
        Roster rosterMocked = new Roster(id, ENPROCESO, players, "rosterMocked");
        Mockito.when(rosterRepository.findById(id)).thenReturn(Optional.of(rosterMocked));

        //Act
        final Optional<Roster> resultOptional = rosterService.findRosterById(id);
        Roster result = resultOptional.get();


        //Assert
        Assertions.assertEquals(rosterMocked, result);
        Mockito.verify(rosterRepository).findById(id);

    }

    @SneakyThrows
    @Test
    void shouldDeleteRosterByIdSUccessfully() {
        //Arrange
        Integer id = 1;
        Player player = new Player();
        Set<Player> players = new HashSet<>(List.of(player));
        Roster rosterMocked = new Roster(id, ENPROCESO, players, "rosterMocked");

        //Act
        rosterService.deleteRosterById(id);

        //Assert
        assertFalse((rosterRepository.findById(id)).isPresent());
        Mockito.verify(rosterRepository).findById(id);
    }

    @SneakyThrows
    @Test
    void shouldParseRosterDtoToEntitySuccessfully(){
        //Arrange
        Integer idRoster = 1;
        Player player1 = new Player(1,"Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Set<Player> players = new HashSet<>(List.of(player1));
        RosterDto rosterDto = new RosterDto(idRoster, ENPROCESO, players, "rosterMocked");

        //Act
        Roster result = rosterService.paseRosterDtoToEntity(rosterDto);

        //Assert
        assertEquals(rosterDto.getId() ,result.getId());
        assertEquals(rosterDto.getState() ,result.getState());
        assertEquals(rosterDto.getPlayers() ,result.getPlayers());
        assertEquals(rosterDto.getName() ,result.getName());
    }

    @SneakyThrows
    @Test
    void shouldParseRosterEntityToDtoSuccessfully(){
        //Arrange
        Integer idRoster = 1;
        Player player1 = new Player(1,"Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Set<Player> players = new HashSet<>(List.of(player1));
        Roster rosterEntity = new Roster(idRoster, ENPROCESO, players, "rosterMocked");

        //Act
        RosterDto result = rosterService.parseRosterEntityToDto(rosterEntity);

        //Assert
        assertEquals(rosterEntity.getId() ,result.getId());
        assertEquals(rosterEntity.getState() ,result.getState());
        assertEquals(rosterEntity.getPlayers() ,result.getPlayers());
        assertEquals(rosterEntity.getName() ,result.getName());
    }

}
