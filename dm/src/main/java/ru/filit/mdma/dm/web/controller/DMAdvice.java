package ru.filit.mdma.dm.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.filit.mdma.dm.exception.WrongDataException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DMAdvice {

    @ExceptionHandler(WrongDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> wrongDataHandler(WrongDataException wrongDataException){
        Map<String, String> error =new HashMap<>();
        error.put("timestamp", new Date().toString());
        error.put("status", HttpStatus.NOT_FOUND.toString());
        error.put("message", wrongDataException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
