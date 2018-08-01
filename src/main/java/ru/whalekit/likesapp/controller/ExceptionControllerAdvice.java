package ru.whalekit.likesapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.whalekit.likesapp.controller.response.ErrorResponse;
import ru.whalekit.likesapp.controller.response.Status;
import ru.whalekit.likesapp.service.exception.PlayerNotFoundException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse> playerNotFoundException(PlayerNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(Status.PLAYER_NOT_FOUND);
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
