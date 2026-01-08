package se.jensen.johan.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Denna fångar alla valideringsfel från @Valid (@NotBlank, @Size, osv.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // Exempel: "text: Text får inte vara tom."
            String message = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            errors.add(message);
        }

        // 400 Bad Request + lista av felmeddelanden
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}