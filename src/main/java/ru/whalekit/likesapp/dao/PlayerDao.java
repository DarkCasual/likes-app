package ru.whalekit.likesapp.dao;

import ru.whalekit.likesapp.domain.Player;

public interface PlayerDao {
    long like(String playerId);
    Player find(String playerId);
}
