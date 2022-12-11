package com.appMundial.lista2026.service.player;

import com.appMundial.lista2026.dto.player.PlayerDto;
import com.appMundial.lista2026.entity.player.Player;
import com.appMundial.lista2026.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    public Player addPlayer(PlayerDto playerDto);

    public Optional<Player> findPlayerById(Integer id);

    public Player updatePlayer(PlayerDto playerDtoEddit, Integer id)throws ResourceNotFoundException;

    public boolean deletePlayerById(Integer id);

    public List<Player> listAllPlayers();

    public void deleteAllPlayers();

    public Player parsePlayerDtoToEntity(PlayerDto playerDto);

    public PlayerDto parsePlayerEntityToDto(Player playerEntity);

    }
