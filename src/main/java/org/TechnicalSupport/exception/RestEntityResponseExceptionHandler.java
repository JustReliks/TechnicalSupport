package org.TechnicalSupport.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class RestEntityResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Map<String, String>> handleException(Exception exception) {
        Map<String, String> response = new HashMap<>();

        response.put("error", exception.getClass().getSimpleName());
        response.put("message", exception.getMessage());
        ResponseStatus annotation = exception.getClass().getAnnotation(ResponseStatus.class);
        int status = HttpStatus.BAD_REQUEST.value();
        if (annotation != null) {
            status = annotation.value().value();
        }
        response.put("status", String.valueOf(status));
        log.error("Something went wrong in REST: {}", exception.getMessage(), exception);
        return ResponseEntity.status(status).body(response);
    }

}
