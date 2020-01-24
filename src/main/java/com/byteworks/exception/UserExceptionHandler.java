package com.byteworks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exception) {

        UserErrorResponse response = new UserErrorResponse();

        if (exception instanceof UserAlreadyExistsException) {

            response.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } else if (exception instanceof UserNotFoundException){

            response.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserErrorResponse(500, "Something went wrong. Please try again", System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
