package com.appMundial.lista2026.repository.roster;

import com.appMundial.lista2026.dto.player.entity.roster.Roster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterRepository extends JpaRepository<Roster, Integer> {
}
