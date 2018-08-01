package ru.whalekit.likesapp.service;

public interface LikeService {
    void like(String playerId);
    long getLikes(String playerId);
}
