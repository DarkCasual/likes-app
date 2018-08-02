package ru.whalekit.likesapp.dao;

import ru.whalekit.likesapp.domain.Player;

import java.util.Optional;

public interface PlayerDao {
    long like(String playerId);
    Optional<Player> find(String playerId);
}
