package ru.whalekit.likesapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.whalekit.likesapp.dao.PlayerDao;
import ru.whalekit.likesapp.domain.Player;
import ru.whalekit.likesapp.service.exception.PlayerNotFoundException;

@Service
public class LikeServiceImpl implements LikeService {
    private final PlayerDao mongoLikesDao;

    @Autowired
    public LikeServiceImpl(PlayerDao mongoLikesDao) {
        this.mongoLikesDao = mongoLikesDao;
    }

    @Override
    public void like(String playerId) {
        if (mongoLikesDao.like(playerId) < 1) {
            throw new PlayerNotFoundException(playerId);
        }
    }

    @Override
    public long getLikes(String playerId) {
        return mongoLikesDao.find(playerId)
                .map(Player::getCount)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
