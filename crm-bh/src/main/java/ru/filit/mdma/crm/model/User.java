package ru.filit.mdma.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String username;
    private String password;
    private Role role;

}
