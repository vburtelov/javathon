package ru.filit.mdma.dm.model;


import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Права доступа к полям сущностей
 */
@Data
public class Access implements Serializable {

  @NotNull
  private Role role;

  @NotNull
  private String entity;

  @NotNull
  private String property;



}

