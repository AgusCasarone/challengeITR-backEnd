package com.appMundial.lista2026.repository.player;

import com.appMundial.lista2026.entity.player.Player;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
