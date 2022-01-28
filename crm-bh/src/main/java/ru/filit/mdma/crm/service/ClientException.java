package ru.filit.mdma.crm.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ClientException extends Exception{
    private HttpStatus status;
    private String body;

    public ClientException(HttpStatus status, String body){
        super();
        this.status=status;
        this.body=body;
    }

    public ClientException(HttpStatus status, Map<String,String> error) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        this.status=status;
        this.body= objectMapper.writeValueAsString(error);
    }
}
