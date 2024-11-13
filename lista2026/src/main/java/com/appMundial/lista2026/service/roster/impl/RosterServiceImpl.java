package com.appMundial.lista2026.service.roster.impl;

import com.appMundial.lista2026.dto.roster.RosterDto;
import com.appMundial.lista2026.dto.player.entity.roster.Roster;
import com.appMundial.lista2026.dto.player.entity.player.Player;
import com.appMundial.lista2026.dto.player.entity.player.Position;
import com.appMundial.lista2026.exception.*;
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

import static com.appMundial.lista2026.dto.player.entity.roster.State.DEFINITIVA;
import static com.appMundial.lista2026.dto.player.entity.roster.State.ENPROCESO;

@Service
public class RosterServiceImpl implements RosterService {

    private static final Logger LOGGER = LogManager.getLogger(RosterServiceImpl.class);

    private static final String rosterDefinitiva = "Roster with id %s state is now DEFINITIVA";
    private static final String rosterAlreadyExists = "There already exist a Roster in the Data Base";
    private static final String rosterFound = "Roster with id %s was found";
    private static final String newRosterAdded = "New roster added";
    private static final String rosterDeleted = "Roster with id %s was deleted";
    private static final String messageRosterNotFound = "Roster not found.";
    private static final String messagePlayerNotFound = "Player not found.";
    private static final String errorRosterNot23Players = "FINAL roster should have exactly 23 players.";
    private static final String messagePlayerAddedToRoster = "Player with id %s added to roster with id %s ";
    private static final String errorRosterCanNotBeDefinitiva = "Roster with id %s can not be DEFINITIVA due to missing conditions.";
    private static final String errorTwoPlayersOfEachPosition = "There should be at least two players with position %s";

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private PlayerServiceImpl playerService;

    @Override
    public Roster addRoster(RosterDto rosterDto) throws RosterAlreadyExistsException {
        Roster rosterEntity = paseRosterDtoToEntity(rosterDto);

        // DELETE THIS VALIDATION TO ALLOW MORE THAN ONE ROSTER TO BE CREATED v
        if (rosterRepository.findAll().size() > 0) {
            RosterAlreadyExistsException e = new RosterAlreadyExistsException(rosterAlreadyExists);
            throw e;
        }
        // DELETE THIS VALIDATION TO ALLOW MORE THAN ONE ROSTER TO BE CREATED ^

        rosterEntity.setState(ENPROCESO);

        LOGGER.info(newRosterAdded);
        return rosterRepository.save(rosterEntity);

    }

    @Override
    public Optional<Roster> findRosterById(Integer id){
        LOGGER.info(String.format(rosterFound, id));
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
            LOGGER.info(String.format(rosterDeleted, id));
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

        Roster rosterOriginal = rosterRepository.findById(idRoster).orElseThrow(()-> new  ResourceNotFoundException(messageRosterNotFound));
        Player player = playerService.findPlayerById(idPlayer).orElseThrow(()->new ResourceNotFoundException(messagePlayerNotFound));

        rosterOriginal.getPlayers().add(player);
        if (rosterOriginal.getState() == DEFINITIVA){
            LOGGER.error(errorRosterNot23Players);
            throw new NoLongerFINALException(errorRosterNot23Players);
        }

        rosterRepository.save(rosterOriginal);
        LOGGER.info(String.format(messagePlayerAddedToRoster, idPlayer, idRoster));

        return rosterOriginal;
    }

    @Override
    public Roster removePlayerFromRoster(Integer idRoster, Integer idPlayer) throws ResourceNotFoundException, NoLongerFINALException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(() -> new ResourceNotFoundException(messageRosterNotFound));


        roster.getPlayers().removeIf(player -> player.getId().equals(idPlayer));
        if (roster.getState() == DEFINITIVA) {
            LOGGER.error(String.format(errorRosterNot23Players));
            throw new NoLongerFINALException(errorRosterNot23Players);
        }
        rosterRepository.save(roster);
        return roster;
    }

    @Override
    public Roster makeRosterDEFINITIVA(Integer idRoster) throws ResourceNotFoundException, FinalRosterCanNotBeChangedException, RosterCanNotBeFINALException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException(messageRosterNotFound));

        if (amountOfPlayersInRoster(idRoster) == 23 && atLeastTwoOfEach(idRoster)) {

            orderNumbers(idRoster);
            roster.setState(DEFINITIVA);

            rosterRepository.save(roster);
            LOGGER.info(String.format(rosterDefinitiva, idRoster));

        } else {
            LOGGER.error(errorRosterCanNotBeDefinitiva);
            throw new FinalRosterCanNotBeChangedException(errorRosterCanNotBeDefinitiva);

        }
        return roster;

    }

    @Override
    public Roster makeRosterENPROCESO(Integer idRoster) throws ResourceNotFoundException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException(messageRosterNotFound));

        roster.setState(ENPROCESO);
        rosterRepository.save(roster);

        return roster;
    }

    @Override
    public Long amountOfPlayersInRoster(Integer idRoster) throws ResourceNotFoundException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException(messageRosterNotFound));

        return (long) roster.getPlayers().size();
    }

    @Override
    public Long amountOfSamePositionInRoster(Integer idRoster, Position position) throws ResourceNotFoundException {
        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException(messageRosterNotFound));

        return roster.getPlayers().stream().filter(player -> player.getPosicion().contains(position)).count();

    }

    @Override
    public boolean atLeastTwoOfEach(Integer idRoster) throws ResourceNotFoundException, RosterCanNotBeFINALException {

        Roster roster = rosterRepository.findById(idRoster).orElseThrow(()-> new ResourceNotFoundException(messageRosterNotFound));

        List<String> missingPositions = new ArrayList<>();
        boolean isTwoOfEach = true;

        if (amountOfSamePositionInRoster(idRoster, Position.ARQUERO) < 2) {
            missingPositions.add(" ARQUERO ");
            isTwoOfEach = false;
        }

        if (amountOfSamePositionInRoster(idRoster, Position.DEFENSOR) < 2) {
            missingPositions.add(" DEFENSOR ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInRoster(idRoster, Position.VOLANTE) < 2) {
            missingPositions.add(" VOLANTE ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInRoster(idRoster, Position.DELANTERO) < 2) {
            missingPositions.add(" DELANTERO ");
            isTwoOfEach = false;
        }

        if (!isTwoOfEach) {
            LOGGER.error(String.format(errorTwoPlayersOfEachPosition, missingPositions));
            throw new RosterCanNotBeFINALException(String.format(errorTwoPlayersOfEachPosition, missingPositions));
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
