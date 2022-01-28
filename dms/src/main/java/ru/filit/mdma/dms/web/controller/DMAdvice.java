package ru.filit.mdma.dms.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.filit.mdma.dms.exception.ClientException;
import ru.filit.mdma.dms.exception.WrongDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DMAdvice {

    @ExceptionHandler(WrongDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> wrongDataHandler(WrongDataException wrongDataException){
        Map<String, String> error =createError(wrongDataException, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<String> clientExceptionHandler(ClientException clientException){
        return ResponseEntity.status(clientException.getStatus()).contentType(MediaType.APPLICATION_JSON).body(clientException.getBody());
    }

    public Map<String, String> createError(Exception e, HttpStatus httpStatus){
        Map<String, String> error =new HashMap<>();
        error.put("timestamp", new Date().toString());
        error.put("status", httpStatus.toString());
        error.put("message", e.getMessage());
        return error;
    }

}
