package com.appMundial.lista2026.controller.roster;

import com.appMundial.lista2026.dto.roster.RosterDto;
import com.appMundial.lista2026.dto.player.entity.roster.Roster;
import com.appMundial.lista2026.exception.*;
import com.appMundial.lista2026.service.roster.impl.RosterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rosters")
@CrossOrigin("*")
public class RosterController {

    @Autowired
    RosterServiceImpl rosterService;

    @PostMapping
    public ResponseEntity<RosterDto> addRoster(@RequestBody RosterDto rosterDto) throws MissingValuesException, Exception {
        return ResponseEntity.ok(rosterService.parseRosterEntityToDto(rosterService.addRoster(rosterDto)));
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Roster>> findRosterById(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(rosterService.findRosterById(id));
    }

    @PostMapping (value = "addPlayer/{idRoster}")
    public ResponseEntity<RosterDto> addPlayerToRoster(@PathVariable Integer idRoster, @RequestParam Integer idPlayer) throws MissingValuesException, NoLongerFINALException, Exception {
        return ResponseEntity.ok(rosterService.parseRosterEntityToDto(rosterService.addPlayerToRoster(idRoster, idPlayer)));
    }

    @DeleteMapping (value = "/removePlayer/{idRoster}")
    public ResponseEntity<RosterDto> removePlayerFromRoster(@PathVariable Integer idRoster, @RequestParam Integer idPlayer) throws ResourceNotFoundException, NoLongerFINALException {
        return ResponseEntity.ok(rosterService.parseRosterEntityToDto(rosterService.removePlayerFromRoster(idRoster,  idPlayer)));
    }

    @PutMapping (value = "/makeRosterDEFINITIVA/{idRoster}")
    public ResponseEntity<RosterDto> makeRosterDEFINITIVA(@PathVariable Integer idRoster) throws ResourceNotFoundException, FinalRosterCanNotBeChangedException, RosterCanNotBeFINALException {
        return ResponseEntity.ok(rosterService.parseRosterEntityToDto(rosterService.makeRosterDEFINITIVA(idRoster)));
    }

    @PutMapping (value = "/makeRosterENPROCESO/{idRoster}")
    public ResponseEntity<RosterDto> makeRosterENPROCESO(@PathVariable Integer idRoster) throws ResourceNotFoundException {
        return ResponseEntity.ok(rosterService.parseRosterEntityToDto(rosterService.makeRosterENPROCESO(idRoster)));
    }
    @DeleteMapping("deleteRoster/{idRoster}")
    public ResponseEntity<String> deleteRoster(@PathVariable Integer idRoster) throws ResourceNotFoundException, Exception {
        rosterService.deleteRosterById(idRoster);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Roster with id %s was deleted.", idRoster));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Roster>> findAllRosters() throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(rosterService.listAllRosters());
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllRosters() throws ResourceNotFoundException {
        rosterService.deleteAllRosters();
        return ResponseEntity.status(HttpStatus.OK).body("All roster were deleted.");
    }

    @GetMapping("/amountOfPlayersInRoster/{idRoster}")
    public ResponseEntity<Long> amountOfPlayersInRoster(@PathVariable Integer idRoster) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(rosterService.amountOfPlayersInRoster(idRoster));
    }

}
