package co.joeportilla.jwtguiback.config;

import co.joeportilla.jwtguiback.dto.ErrorDto;
import co.joeportilla.jwtguiback.exception.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * With this class, the app can intercept the custom exception and return the given message and the http code
 */
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                             .body(new ErrorDto(ex.getMessage()));
    }
}
