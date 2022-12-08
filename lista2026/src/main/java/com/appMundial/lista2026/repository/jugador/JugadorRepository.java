package com.appMundial.lista2026.repository.jugador;

import com.appMundial.lista2026.entity.jugador.Jugador;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

}
