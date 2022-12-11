package com.appMundial.lista2026.service.roster;

import com.appMundial.lista2026.dto.roster.RosterDto;
import com.appMundial.lista2026.entity.player.Position;
import com.appMundial.lista2026.entity.roster.Roster;
import com.appMundial.lista2026.exception.*;

import java.util.List;
import java.util.Optional;

public interface RosterService {

    public Roster addRoster(RosterDto rosterDto) throws RosterAlreadyExistsException;

    public Optional<Roster> findRosterById(Integer id);

    public List<Roster> listAllRosters();

    public boolean deleteRosterById(Integer id);

    public void deleteAllRosters();

    public Roster addPlayerToRoster(Integer idLista, Integer idJugador) throws ResourceNotFoundException, NoLongerFINALException;

    Roster removePlayerFromRoster(Integer idRoster, Integer idPlayer) throws ResourceNotFoundException, NoLongerFINALException;

    public Roster makeRosterDEFINITIVA(Integer idLista) throws ResourceNotFoundException, FinalRosterCanNotBeChangedException, RosterCanNotBeFINALException;

    Roster makeRosterENPROCESO(Integer idRoster) throws ResourceNotFoundException;

    public Long amountOfPlayersInRoster(Integer idLista) throws ResourceNotFoundException;

    public Long amountOfSamePositionInRoster(Integer idLista, Position position) throws ResourceNotFoundException;

    public boolean atLeastTwoOfEach(Integer idLista) throws ResourceNotFoundException, RosterCanNotBeFINALException;

    void orderNumbers(Integer id);

    public Roster paseRosterDtoToEntity(RosterDto rosterDto);

    public RosterDto parseRosterEntityToDto(Roster rosterEntity);

    }
