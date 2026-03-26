package lk.ijse.plantgrowthtracking.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import lk.ijse.plantgrowthtracking.exception.AlreadyExistsException;
import lk.ijse.plantgrowthtracking.exception.EmailNotFoundException;
import lk.ijse.plantgrowthtracking.exception.BadCredentialsException;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<APIResponse<String>> handleAlreadyExistsException(AlreadyExistsException ex) {
        APIResponse<String> response = new APIResponse<>(409, "Already exists", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<APIResponse<String>> handleEmailNotFoundException(EmailNotFoundException ex) {
        APIResponse<String> response = new APIResponse<>(404, "Email not found", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        APIResponse<String> response = new APIResponse<>(401, "Bad credentials", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        APIResponse<Object> response = new APIResponse<>(400, "Validation Error", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleGlobalException(Exception ex) {
        APIResponse<String> response = new APIResponse<>(500, "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}