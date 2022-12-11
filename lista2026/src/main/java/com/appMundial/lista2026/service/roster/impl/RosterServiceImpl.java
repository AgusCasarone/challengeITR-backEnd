package com.appMundial.lista2026.service.roster.impl;

import com.appMundial.lista2026.dto.roster.RosterDto;
import com.appMundial.lista2026.entity.roster.Roster;
import com.appMundial.lista2026.entity.player.Player;
import com.appMundial.lista2026.entity.player.Position;
import com.appMundial.lista2026.exception.RosterAlreadyExistsException;
import com.appMundial.lista2026.exception.NoLongerFINALException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.roster.RosterRepository;
import com.appMundial.lista2026.service.player.impl.PlayerServiceImpl;
import com.appMundial.lista2026.service.roster.RosterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.appMundial.lista2026.entity.roster.State.DEFINITIVA;
import static com.appMundial.lista2026.entity.roster.State.ENPROCESO;

@Service
public class RosterServiceImpl implements RosterService {

    private static final Logger LOGGER = LogManager.getLogger(RosterServiceImpl.class);

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private PlayerServiceImpl playerService;

    @Override
    public Roster addRoster(RosterDto rosterDto) throws RosterAlreadyExistsException {
        Roster rosterEntity = paseRosterDtoToEntity(rosterDto);

        // DELETE THIS VALIDATION TO ALLOW MORE THAN ONE ROSTER TO BE CREATED v
        if (rosterRepository.findAll().size() > 0) {
            RosterAlreadyExistsException e = new RosterAlreadyExistsException("Ya hay una lista en la base de datos");
            throw e;
        }
        // DELETE THIS VALIDATION TO ALLOW MORE THAN ONE ROSTER TO BE CREATED ^

        rosterEntity.setState(ENPROCESO);

        LOGGER.info("New roster added");
                                                                                                    return rosterRepository.save(rosterEntity);

    }

    @Override
    public Optional<Roster> findRosterById(Integer id){
        LOGGER.info(String.format("Roster with id %s was found", id));
        return rosterRepository.findById(id);
    }

    @Override
    public List<Roster> listAllRosters() {
        return rosterRepository.findAll();
    }

    @Override
    public boolean deleteRosterById(Integer id){
        if (rosterRepository.existsById(id)) {
            rosterRepository.deleteById(id);
            LOGGER.info(String.format("Roster with id %s was deleted", id));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllRosters(){
        rosterRepository.deleteAll();
    }

    @Override
    public Roster addPlayerToRoster(Integer idRoster, Integer idPlayer) throws ResourceNotFoundException, NoLongerFINALException {

        Roster rosterOriginal = rosterRepository.findById(idRoster).orElseThrow(()-> new  ResourceNotFoundException("Roster not found."));
        Player player = playerService.findPlayerById(idPlayer).orElseThrow(()->new ResourceNotFoundException("Player not found."));

        rosterOriginal.getPlayers().add(player);
        if (amountOfPlayersInRoster(idRoster) != 23 && rosterOriginal.getState() == DEFINITIVA){
            NoLongerFINALException e = new NoLongerFINALException("Roster would no longer have 23 players. Change roster.state to ENPROCESO and try again.");
            throw e;
        }

        rosterRepository.save(rosterOriginal);
        LOGGER.info(String.format("Player with id %s added to roster with id %s ", idPlayer, idRoster));

        return rosterOriginal;
    }

    @Override
    public Roster removePlayerFromRoster(Integer idRoster, Integer idPlayer) throws ResourceNotFoundException, NoLongerFINALException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(() -> new ResourceNotFoundException("No se encontró la roster."));


        roster.getPlayers().removeIf(player -> player.getId().equals(idPlayer));
        if (amountOfPlayersInRoster(idRoster) != 23 && roster.getState() == DEFINITIVA) {
            NoLongerFINALException e = new NoLongerFINALException("Roster would no longer have 23 players. Change roster.state to ENPROCESO and try again.");
            throw e;
        }
        rosterRepository.save(roster);
        return roster;
    }

    @Override
    public Roster makeRosterDEFINITIVA(Integer idRoster) throws ResourceNotFoundException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException("Roster not found"));

        if (amountOfPlayersInRoster(idRoster) == 23 && atLeastTwoOfEach(idRoster)) {

            orderNumbers(idRoster);
            roster.setState(DEFINITIVA);

            rosterRepository.save(roster);
            LOGGER.info(String.format("Roster with id %s state is now DEFINITIVA", idRoster));

        } else {

            LOGGER.info(String.format("Roster with id %s can not be DEFINITIVA due to missing conditions.", idRoster));

        }
        return roster;

    }

    @Override
    public Roster makeRosterENPROCESO(Integer idRoster) throws ResourceNotFoundException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException("Roster not found"));

        roster.setState(ENPROCESO);
        rosterRepository.save(roster);

        return roster;
    }

    @Override
    public Long amountOfPlayersInRoster(Integer idRoster) throws ResourceNotFoundException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException("Roster not found"));

        return (long) roster.getPlayers().size();
    }

    @Override
    public Long amountOfSamePositionInRoster(Integer idRoster, Position position) throws ResourceNotFoundException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException("Roster not found"));

        return roster.getPlayers().stream().filter(player -> player.getPosicion().equals(position)).count();

    }

    @Override
    public boolean atLeastTwoOfEach(Integer idRoster) throws ResourceNotFoundException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException("Roster not found"));

        List<String> missingPositions = new ArrayList<>();
        boolean isTwoOfEach = true;

        if (amountOfSamePositionInRoster(idRoster, Position.ARQUERO) >= 2) {
            missingPositions.add(" ARQUERO ");
            isTwoOfEach = false;
        }

        if (amountOfSamePositionInRoster(idRoster, Position.DEFENSOR) >= 2) {
            missingPositions.add(" DEFENSOR ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInRoster(idRoster, Position.VOLANTE) >= 2) {
            missingPositions.add(" VOLANTE ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInRoster(idRoster, Position.DELANTERO) >= 2) {
            missingPositions.add(" DELANTERO ");
            isTwoOfEach = false;
        }

        if (!isTwoOfEach) {
            LOGGER.error(String.format("There should be at least two players with position %s", missingPositions));
        }


        return isTwoOfEach;
    }

    @Override
    public void orderNumbers(Integer id) {

        boolean hasAllNumbers = false;

        Set<Player> players = (rosterRepository.getById(id)).getPlayers();

        Integer newNumber = 1;

        for(Player player : players){

            player.setNumber(newNumber);
            newNumber++;
        }
    }

    @Override
    public Roster paseRosterDtoToEntity(RosterDto rosterDto){

        Roster rosterEntity = new Roster();

        rosterEntity.setId(rosterDto.getId());
        rosterEntity.setState(rosterDto.getState());
        rosterEntity.setPlayers(rosterDto.getPlayers());
        rosterEntity.setName(rosterDto.getName());

        return rosterEntity;
    }

    @Override
    public RosterDto parseRosterEntityToDto(Roster rosterEntity){

        RosterDto rosterDto = new RosterDto();

        rosterDto.setId(rosterEntity.getId());
        rosterDto.setState(rosterEntity.getState());
        rosterDto.setPlayers(rosterEntity.getPlayers());
        rosterDto.setName(rosterEntity.getName());

        return rosterDto;
    }

}
