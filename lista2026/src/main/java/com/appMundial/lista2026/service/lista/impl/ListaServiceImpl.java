package com.appMundial.lista2026.service.lista.impl;

import com.appMundial.lista2026.dto.lista.ListaDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.entity.jugador.Posicion;
import com.appMundial.lista2026.entity.lista.Estado;
import com.appMundial.lista2026.entity.lista.Lista;
import com.appMundial.lista2026.exception.ListaDeJugadoresAlreadyExistsException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.lista.ListaRepository;
import com.appMundial.lista2026.service.jugador.impl.JugadorServiceImpl;
import com.appMundial.lista2026.service.lista.ListaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListaServiceImpl implements ListaService {

    private static final Logger LOGGER = LogManager.getLogger(ListaServiceImpl.class);

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private JugadorServiceImpl jugadorRepository;

    @Override
    public Lista addLista(ListaDto listaDto) throws ListaDeJugadoresAlreadyExistsException {
        Lista listaEntity = paseListaDtoToEntity(listaDto);

        // DELETE THIS VALIDATION TO ALLOW MORE THAN ONE LISTA TO BE CREATED
        if (listaRepository.findAll().size() > 0) {
            ListaDeJugadoresAlreadyExistsException e = new ListaDeJugadoresAlreadyExistsException("Ya hay una lista en la base de datos");
            throw e;
        }

        listaEntity.setEstado(Estado.ENPROCESO);

        LOGGER.info("Se creó una nueva lista");
        return listaRepository.save(listaEntity);

    }

    @Override
    public Optional<Lista> findListaById(Integer id){
        LOGGER.info(String.format("Se encontró la lista con id %s ", id));
        return listaRepository.findById(id);
    }

    @Override
    public List<Lista> listAllListas() {
        return listaRepository.findAll();
    }

    @Override
    public boolean deleteListaById(Integer id){
        if (listaRepository.existsById(id)) {
            listaRepository.deleteById(id);
            LOGGER.info(String.format("Se eliminó la lista con id %s", id));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllListasExistentes(){
        listaRepository.deleteAll();
    }

    @Override
    public Lista addJugadorALista(Integer idLista, Integer idJugador) throws ResourceNotFoundException {

        Lista listaOriginal = listaRepository.findById(idLista).orElseThrow(()-> new  ResourceNotFoundException("No se encontró la lista."));
        Jugador jugador = jugadorRepository.findJugadorById(idJugador).orElseThrow(()->new ResourceNotFoundException("No se encontró el jugador."));

        listaOriginal.getJugadores().add(jugador);

        listaRepository.save(listaOriginal);
        LOGGER.info(String.format("Se agregó el jugador %s a la lista con id %s ", idJugador, idLista));

        return listaOriginal;
    }

    @Override
    public Lista makeListaEstadoDEFINITIVA(Integer idLista) throws ResourceNotFoundException {

        //chequear que los jugadores.getNumero() sean secuenciales del 1 al 23

        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        if (amountOfJugadoresInLista(idLista) == 23 && atLeastTwoOfEach(idLista)) {

            lista.setEstado(Estado.DEFINITIVA);

            listaRepository.save(lista);
            LOGGER.info(String.format("Se cambió el estado de la lista con id %s", idLista));
            return lista;

        } else {

            LOGGER.info(String.format("La lista %s no puede ser DEFINITIVA porque no cumple las condiciones.", idLista));
            return lista;

        }

    }

    @Override
    public Long amountOfJugadoresInLista(Integer idLista) throws ResourceNotFoundException {

        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        return (long) lista.getJugadores().size();
    }

    @Override
    public Long amountOfSamePositionInLista(Integer idLista, Posicion posicion) throws ResourceNotFoundException {
        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        return lista.getJugadores().stream().filter(j -> j.getPosicion().equals(posicion)).count();

    }

    @Override
    public boolean atLeastTwoOfEach(Integer idLista) throws ResourceNotFoundException {

        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        List<String> missingConditions = new ArrayList<>();
        boolean isTwoOfEach = true;

        if (amountOfSamePositionInLista(idLista, Posicion.ARQUERO) > 2) {
            missingConditions.add(" arqueros ");
            isTwoOfEach = false;
        }

        if (amountOfSamePositionInLista(idLista, Posicion.DEFENSOR) > 2) {
            missingConditions.add(" defensores ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInLista(idLista, Posicion.VOLANTE) > 2) {
            missingConditions.add(" volantes ");
            isTwoOfEach = false;
        }
        if (amountOfSamePositionInLista(idLista, Posicion.DELANTERO) > 2) {
            missingConditions.add(" delanteros ");
            isTwoOfEach = false;
        }

        if (!isTwoOfEach) {
            LOGGER.error(String.format("Debería haber al menos 2 jugadores de cada tipo y no hay suficientes %s", missingConditions));
        }


        return isTwoOfEach;
    }

    public boolean sequenceOfTwentythreeJugadorNumero(Integer id) {

        boolean hasAllNumbers = false;

        Lista lista = (listaRepository.getById(id)).;

        for (Jugador j : lista.getJugadores()) {
            if (j.getNumero() > 23){
                LOGGER.error(String.format("Hay al menos un jugador con un número mayor a 23."));
                return hasAllNumbers;
            } else if (j.getNumero()) {
                
            }
        }

        return hasAllNumbers;
    }

    @Override
    public Lista paseListaDtoToEntity(ListaDto listaDto){

        Lista listaEntity = new Lista();

        listaEntity.setId(listaDto.getId());
        listaEntity.setEstado(listaDto.getEstado());
        listaEntity.setJugadores(listaDto.getJugadores());
        listaEntity.setNombre(listaDto.getNombre());

        return listaEntity;
    }

    @Override
    public ListaDto parseListaEntityToDto(Lista listaEntity){

        ListaDto listaDto = new ListaDto();

        listaDto.setId(listaEntity.getId());
        listaDto.setEstado(listaEntity.getEstado());
        listaDto.setJugadores(listaEntity.getJugadores());
        listaDto.setNombre(listaEntity.getNombre());

        return listaDto;
    }

}
