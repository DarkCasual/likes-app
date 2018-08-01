package ru.whalekit.likesapp.controller.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private Status status;
    private String message;
}
