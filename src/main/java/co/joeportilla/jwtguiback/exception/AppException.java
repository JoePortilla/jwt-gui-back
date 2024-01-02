package co.joeportilla.jwtguiback.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception that is intercepted by RestExceptionHandler
 */
@Getter
public class AppException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
