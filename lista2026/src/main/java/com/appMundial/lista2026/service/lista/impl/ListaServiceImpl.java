package com.appMundial.lista2026.service.lista.impl;

import com.appMundial.lista2026.dto.lista.ListaDto;
import com.appMundial.lista2026.entity.jugador.Jugador;
import com.appMundial.lista2026.entity.jugador.Posicion;
import com.appMundial.lista2026.entity.lista.Estado;
import com.appMundial.lista2026.entity.lista.Lista;
import com.appMundial.lista2026.exception.ResourceNotFoundException;
import com.appMundial.lista2026.repository.lista.ListaRepository;
import com.appMundial.lista2026.service.jugador.impl.JugadorServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class ListaServiceImpl {

    private static final Logger LOGGER = LogManager.getLogger(ListaServiceImpl.class);

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private JugadorServiceImpl jugadorRepository;

    public Lista addLista(ListaDto listaDto) {
        Lista listaEntity = paseListaDtoToEntity(listaDto);
        listaEntity.setEstado(Estado.ENPROCESO);
        LOGGER.info("Se creó una nueva lista");
        return listaRepository.save(listaEntity);
    }

    public Optional<Lista> findListaById(Integer id){
        LOGGER.info(String.format("Se encontró la lista con id %s ", id));
        return listaRepository.findById(id);
    }

    public List<Lista> listAllListas() {
        return listaRepository.findAll();
    }

    public boolean deleteListaById(Integer id){
        if (listaRepository.existsById(id)) {
            listaRepository.deleteById(id);
            LOGGER.info(String.format("Se eliminó la lista con id %s", id));
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllListasExistentes(){
        listaRepository.deleteAll();
    }

    public Lista addJugadorALista(Integer idLista, Integer idJugador) throws ResourceNotFoundException {

        Lista listaOriginal = listaRepository.findById(idLista).orElseThrow(()-> new  ResourceNotFoundException("No se encontró la lista."));
        Jugador jugador = jugadorRepository.findJugadorById(idJugador).orElseThrow(()->new ResourceNotFoundException("No se encontró el jugador."));

        listaOriginal.getJugadores().add(jugador);

        listaRepository.save(listaOriginal);
        LOGGER.info(String.format("Se agregó el jugador %s a la lista con id %s ", idJugador, idLista));

        return listaOriginal;
    }

    public Lista makeListaEstadoDEFINITIVA(Integer idLista) throws ResourceNotFoundException {

        //chequear que los jugadores.getNumero() sean secuenciales del 1 al 23

        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        long cantidadArqueros = lista.getJugadores().stream().filter(j -> j.getPosicion().equals(Posicion.ARQUERO)).count();

        if (cantidadArqueros == 2 && lista.getJugadores().size() == 23) {
            lista.setEstado(Estado.DEFINITIVA);
            listaRepository.save(lista);
        }
        LOGGER.info(String.format("Se cambió el estado de la lista con id %s", idLista));
        return lista;
    }

    public Long amountOfJugadoresInLista(Integer idLista) throws ResourceNotFoundException {

        Lista lista = listaRepository.findById(idLista).orElseThrow(()-> new ResourceNotFoundException("No se encontró la lista"));

        return lista.getJugadores().stream().count();
    }

    public Lista paseListaDtoToEntity(ListaDto listaDto){

        Lista listaEntity = new Lista();

        listaEntity.setId(listaDto.getId());
        listaEntity.setEstado(listaDto.getEstado());
        listaEntity.setJugadores(listaDto.getJugadores());
        listaEntity.setNombre(listaDto.getNombre());

        return listaEntity;
    }

    public ListaDto parseListaEntityToDto(Lista listaEntity){

        ListaDto listaDto = new ListaDto();

        listaDto.setId(listaEntity.getId());
        listaDto.setEstado(listaEntity.getEstado());
        listaDto.setJugadores(listaEntity.getJugadores());
        listaDto.setNombre(listaEntity.getNombre());

        return listaDto;
    }

}
