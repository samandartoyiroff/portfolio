package uz.tuit.portfolio.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    // Handle global exceptions for any unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }




   /* @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        if (message != null && message.contains("duplicate key value")) {
            message = "Bunday nomli ma'lumot allaqachon mavjud!";
        } else {
            message = "Ma'lumotlar yaxlitligi buzildi (Data Integrity Violation).";
        }

        ErrorMessage errorMessage = new ErrorMessage(message, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }*/




}
