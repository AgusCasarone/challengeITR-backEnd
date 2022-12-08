package com.appMundial.lista2026.repository.lista;

import com.appMundial.lista2026.entity.lista.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Integer> {
}
