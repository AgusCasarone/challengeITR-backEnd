package com.appMundial.lista2026.service.player;

import com.appMundial.lista2026.dto.player.PlayerDto;
import com.appMundial.lista2026.entity.player.Player;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.service.player.impl.PlayerServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.appMundial.lista2026.entity.player.Position.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PlayerServiceImplTest {

    @Autowired
    private PlayerServiceImpl playerService;

    @Test
    void addPlayer() {
        //Arrange
        PlayerDto playerDto = new PlayerDto("Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        Player result = playerService.addPlayer(playerDto);

        //Assert
        assertNotNull(result);
    }

    @Test
    void findPlayerById() throws ResourceNotFoundException {
        //Arrange
        Integer id = 12;
        PlayerDto playerDto = new PlayerDto(id, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        playerService.findPlayerById(playerDto.getId());

        //Assert
        assertEquals(id, playerDto.getId());
    }

    @SneakyThrows
    @Test
    void updatePlayer() {
        //Arrange
        PlayerDto playerDto = new PlayerDto(1,"Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Player start = playerService.addPlayer(playerDto);
        PlayerDto playerDto1 = new PlayerDto(2,"Lionel", "Messi", 34, "PSG", java.util.List.of(VOLANTE, ARQUERO), 13);

        //Act
        Player result = playerService.updatePlayer(playerDto1, 1);

        //Assert
        assertEquals(start.getId(), result.getId());
        assertNotEquals(start.getName() ,result.getName());
        assertNotEquals(start.getLastName() ,result.getLastName());
        assertNotEquals(start.getAge() ,result.getAge());
        assertNotEquals(start.getTeam() ,result.getTeam());
        assertNotEquals(start.getPosicion() ,result.getPosicion());
        assertNotEquals(start.getNumber() ,result.getNumber());
    }

    @Test
    void deletePlayerByIdTrue() throws ResourceNotFoundException {
        //Arrange
        PlayerDto playerDto = new PlayerDto("Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Player playerSaved = playerService.addPlayer(playerDto);

        //Act
        playerService.deletePlayerById(playerSaved.getId());

        //Assert
        Optional<Player> result = playerService.findPlayerById(playerSaved.getId());
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void deletePlayerByIdFalse() throws ResourceNotFoundException {
        //Arrange
        PlayerDto playerDto = new PlayerDto(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);
        Player playerSaved = playerService.addPlayer(playerDto);

        //Act
        playerService.deletePlayerById(2);

        //Assert
        assertFalse(playerService.findPlayerById(2).isPresent());
    }

    @Test
    void parsePlayerDtoToEntity(){
        //Arrange
        PlayerDto playerDto = new PlayerDto(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        Player player = playerService.parsePlayerDtoToEntity(playerDto);

        //Assert
        assertEquals(player.getAge(), playerDto.getAge());
    }

    @Test
    void parsePlayerEntityToDto(){
        //Arrange
        Player player = new Player(1, "Dibu", "Martinez", 30, "Aston Villa", java.util.List.of(VOLANTE, ARQUERO, DELANTERO), 1);

        //Act
        PlayerDto playerDto = playerService.parsePlayerEntityToDto(player);

        //Assert
        assertEquals(player.getAge(), playerDto.getAge());

    }


}
