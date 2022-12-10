package com.appMundial.lista2026.service.lista;

import com.appMundial.lista2026.dto.lista.ListaDto;
import com.appMundial.lista2026.entity.jugador.Posicion;
import com.appMundial.lista2026.entity.lista.Lista;
import com.appMundial.lista2026.exception.ListaDeJugadoresAlreadyExistsException;
import com.appMundial.lista2026.exception.NoLongerFINALException;
import com.appMundial.lista2026.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ListaService {

    public Lista addLista(ListaDto listaDto) throws ListaDeJugadoresAlreadyExistsException;

    public Optional<Lista> findListaById(Integer id);

    public List<Lista> listAllListas();

    public boolean deleteListaById(Integer id);

    public void deleteAllListasExistentes();

    public Lista addJugadorALista(Integer idLista, Integer idJugador) throws ResourceNotFoundException, NoLongerFINALException;

    public Lista makeListaEstadoDEFINITIVA(Integer idLista) throws ResourceNotFoundException;

    public Long amountOfJugadoresInLista(Integer idLista) throws ResourceNotFoundException;

    public Long amountOfSamePositionInLista(Integer idLista, Posicion posicion) throws ResourceNotFoundException;

    public boolean atLeastTwoOfEach(Integer idLista) throws ResourceNotFoundException;

    public Lista paseListaDtoToEntity(ListaDto listaDto);

    public ListaDto parseListaEntityToDto(Lista listaEntity);

    }
