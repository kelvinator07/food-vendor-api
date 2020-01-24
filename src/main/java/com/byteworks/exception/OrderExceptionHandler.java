package com.byteworks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<OrderErrorResponse> handleException(Exception exception) {

        OrderErrorResponse response = new OrderErrorResponse();

         if (exception instanceof OrderNotFoundException || exception instanceof MealNotFoundException){

            response.setMessage(exception.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new OrderErrorResponse(500, "Something went wrong. Please try again", System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
