package cafe.shop.controller;

import cafe.shop.exception.ResourceNotFoundException;
import cafe.shop.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoreController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            HttpMessageNotReadableException.class
        }
    )
    ErrorResponse handlerException(Exception e) {
        return new ErrorResponse(e.getClass().getSimpleName(), e.getMessage());
    }
}
