package com.appMundial.lista2026.controller.Player;

import com.appMundial.lista2026.dto.player.PlayerDto;
import com.appMundial.lista2026.entity.player.Player;
import com.appMundial.lista2026.exception.MissingValuesException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.service.player.impl.PlayerServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@CrossOrigin("*")
public class PlayerController {

    private static final Logger LOGGER = LogManager.getLogger(PlayerController.class);

    @Autowired
    private PlayerServiceImpl playerService;

    @PostMapping
    public ResponseEntity<PlayerDto> addPlayer(@RequestBody PlayerDto playerDto) throws MissingValuesException, Exception {

        return ResponseEntity.ok(playerService.parsePlayerEntityToDto(playerService.addPlayer(playerDto)));
    }

    @GetMapping(value = {"/{idPlayer}"})
    public ResponseEntity<Optional<Player>> findPlayerById(@PathVariable Integer idPlayer) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(playerService.findPlayerById(idPlayer));
    }

    @PutMapping (value = "/{idPlayer}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Integer idPlayer, @RequestBody PlayerDto playerDto) throws MissingValuesException, Exception {
        return ResponseEntity.ok(playerService.updatePlayer(playerDto, idPlayer));
    }

    @DeleteMapping("/{idPlayer}")
    public ResponseEntity<String> deletePlayer(@PathVariable Integer idPlayer) throws ResourceNotFoundException, Exception {
        playerService.deletePlayerById(idPlayer);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("The player with id %s was deleted", idPlayer));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Player>> findAllPlayers() throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(playerService.listAllPlayers());
    }

    @DeleteMapping("/deleteAllPlayers")
    public ResponseEntity<String> deleteAllPlayer() throws ResourceNotFoundException, Exception {
        playerService.deleteAllPlayers();
        return ResponseEntity.status(HttpStatus.OK).body("All players were deleted");
    }

}
