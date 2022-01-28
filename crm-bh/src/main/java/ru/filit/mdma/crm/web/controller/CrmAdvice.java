package ru.filit.mdma.crm.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.filit.mdma.crm.service.ClientException;

@ControllerAdvice
public class CrmAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<String> clientExceptionHandler(ClientException clientException){
        return ResponseEntity.status(clientException.getStatus()).contentType(MediaType.APPLICATION_JSON).body(clientException.getBody());
    }

}
