package ru.whalekit.likesapp.service.exception;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String playerId) {
        super(String.format("Player %s doesn't exist", playerId));
    }
}
