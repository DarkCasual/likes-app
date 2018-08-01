package ru.whalekit.likesapp.controller.response;

import lombok.Data;

@Data
public class GetLikesResponse {
    private final String playerId;
    private final long likes;
}
