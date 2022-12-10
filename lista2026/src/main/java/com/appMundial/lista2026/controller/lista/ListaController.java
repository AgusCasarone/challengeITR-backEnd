package com.appMundial.lista2026.controller.lista;

import com.appMundial.lista2026.dto.lista.ListaDto;
import com.appMundial.lista2026.entity.lista.Lista;
import com.appMundial.lista2026.exception.MissingValuesException;
import com.appMundial.lista2026.exception.NoLongerFINALException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.service.lista.impl.ListaServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    ListaServiceImpl listaService;

    @PostMapping
    public ResponseEntity<ListaDto> addLista(@RequestBody ListaDto listaDto) throws MissingValuesException, Exception {
        return ResponseEntity.ok(listaService.parseListaEntityToDto(listaService.addLista(listaDto)));
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Optional<Lista>> findListaById(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(listaService.findListaById(id));
    }

    @PostMapping (value = "addJugador/{idLista}")
    public ResponseEntity<ListaDto> addJugadorALaLista(@PathVariable Integer idLista, @RequestParam Integer idJugador) throws MissingValuesException, NoLongerFINALException, Exception {
        return ResponseEntity.ok(listaService.parseListaEntityToDto(listaService.addJugadorALista(idLista, idJugador)));
    }

    @DeleteMapping (value = "/removeJugador/{idLista}")
    public ResponseEntity<ListaDto> removeJugadorFromLista(@PathVariable Integer idLista, @RequestParam Integer idJugador) throws ResourceNotFoundException, NoLongerFINALException {
        return ResponseEntity.ok(listaService.parseListaEntityToDto(listaService.removeJugadorFromLista(idLista,  idJugador)));
    }

    @PutMapping (value = "/makeListaDEFINITIVA/{idLista}")
    public ResponseEntity<ListaDto> makeListaEstadoDEFINITIVA(@PathVariable Integer idLista) throws ResourceNotFoundException {
        return ResponseEntity.ok(listaService.parseListaEntityToDto(listaService.makeListaEstadoDEFINITIVA(idLista)));
    }

    @PutMapping (value = "/makeListaENPROCESO/{idLista}")
    public ResponseEntity<ListaDto> makeListaENPROCESO(@PathVariable Integer idLista) throws ResourceNotFoundException {
        return ResponseEntity.ok(listaService.parseListaEntityToDto(listaService.makeListaENPROCESO(idLista)));
    }
    @DeleteMapping("deleteLista/{id}")
    public ResponseEntity<String> deleteLista(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        listaService.deleteListaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Se eliminó la lista con id %s", id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Lista>> findAllListas() throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(listaService.listAllListas());
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllListasExistentes() throws ResourceNotFoundException {
        listaService.deleteAllListasExistentes();
        return ResponseEntity.status(HttpStatus.OK).body("Se eliminaron todas las listas");
    }

    @GetMapping("/countJugadores/{id}")
    public ResponseEntity<Long> amountOfJugadoresInLista(@PathVariable Integer id) throws ResourceNotFoundException, Exception {
        return ResponseEntity.ok(listaService.amountOfJugadoresInLista(id));
    }

}
