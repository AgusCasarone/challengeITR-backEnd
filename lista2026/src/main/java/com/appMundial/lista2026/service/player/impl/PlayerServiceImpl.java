package com.appMundial.lista2026.service.player.impl;

import com.appMundial.lista2026.dto.player.PlayerDto;
import com.appMundial.lista2026.dto.player.entity.player.Player;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.player.PlayerRepository;
import com.appMundial.lista2026.service.player.PlayerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Logger LOGGER = LogManager.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player addPlayer(PlayerDto playerDto) {
        Player playerEntity = parsePlayerDtoToEntity(playerDto);
        LOGGER.info(String.format("New player:" +
                "\n Name: %s " +
                "\n Last Name: %s " +
                "\n Age: %s " +
                "\n Team: %s " +
                "\n Position: %s " +
                "\n Number: %s ", playerDto.getName(), playerDto.getLastName(), playerDto.getAge(), playerDto.getTeam(), playerDto.getPosition(), playerDto.getNumber()));
        return playerRepository.save(playerEntity);
    }

    @Override
    public Optional<Player> findPlayerById(Integer id) throws ResourceNotFoundException {
        if (playerRepository.existsById(id)) {
            LOGGER.info(String.format("Player with id %s was found", id));
            return playerRepository.findById(id);

        } else {
            throw new ResourceNotFoundException(String.format("No player with id %s was found", id));
        }
    }

    @Override
    public Player updatePlayer(PlayerDto playerDtoEddit, Integer id) throws ResourceNotFoundException {

        PlayerDto playerDtoOriginal = parsePlayerEntityToDto(playerRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Player not found")));

        if (playerDtoEddit.getName() != null){
            playerDtoOriginal.setName(playerDtoEddit.getName());
        }
        if (playerDtoEddit.getLastName() != null){
            playerDtoOriginal.setLastName(playerDtoEddit.getLastName());
        }
        if (playerDtoEddit.getAge() != null){
            playerDtoOriginal.setAge(playerDtoEddit.getAge());
        }
        if (playerDtoEddit.getTeam() != null){
            playerDtoOriginal.setTeam(playerDtoEddit.getTeam());
        }
        if (playerDtoEddit.getPosition() != null) {
            playerDtoOriginal.setPosition(playerDtoEddit.getPosition());
        }
        if (playerDtoEddit.getNumber() != null) {
            playerDtoOriginal.setNumber(playerDtoEddit.getNumber());
        }

        playerDtoOriginal.setId(id);

        LOGGER.info(String.format("Player with id %s was updated", id));
        playerRepository.save(parsePlayerDtoToEntity(playerDtoOriginal));
        return parsePlayerDtoToEntity(playerDtoOriginal);

    }

    @Override
    public boolean deletePlayerById(Integer id) throws ResourceNotFoundException {
        if (playerRepository.existsById(id)) {
            LOGGER.info(String.format("Player with id %s was deleted", id));
            playerRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException(String.format("No player with id %s was found", id));
        }
    }

    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }


    public void deleteAllPlayers() {
        LOGGER.info("All players were deleted");
        playerRepository.deleteAll();
    }

    public Player parsePlayerDtoToEntity(PlayerDto playerDto) {

        Player playerEntity = new Player();

        playerEntity.setId(playerDto.getId());
        playerEntity.setName(playerDto.getName());
        playerEntity.setLastName(playerDto.getLastName());
        playerEntity.setAge(playerDto.getAge());
        playerEntity.setTeam(playerDto.getTeam());
        playerEntity.setTeam(playerDto.getTeam());
        playerEntity.setPosicion(playerDto.getPosition());
        playerEntity.setNumber(playerDto.getNumber());

        return playerEntity;
    }

    public PlayerDto parsePlayerEntityToDto(Player playerEntity) {

        PlayerDto playerDto = new PlayerDto();

        playerDto.setId(playerEntity.getId());
        playerDto.setName(playerEntity.getName());
        playerDto.setLastName(playerEntity.getLastName());
        playerDto.setAge(playerEntity.getAge());
        playerDto.setTeam(playerEntity.getTeam());
        playerDto.setPosition(playerEntity.getPosicion());
        playerDto.setNumber(playerEntity.getNumber());

        return playerDto;
    }


}